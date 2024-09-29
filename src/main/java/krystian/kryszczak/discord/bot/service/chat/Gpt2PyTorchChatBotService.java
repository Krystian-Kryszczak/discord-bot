package krystian.kryszczak.discord.bot.service.chat;

import ai.djl.huggingface.tokenizers.Encoding;
import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import ai.djl.inference.Predictor;
import ai.djl.modality.nlp.generate.CausalLMOutput;
import ai.djl.modality.nlp.generate.SearchConfig;
import ai.djl.modality.nlp.generate.TextGenerator;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.DeferredTranslatorFactory;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;

@Singleton
public final class Gpt2PyTorchChatBotService implements ChatBotService, AutoCloseable {
    private final ZooModel<NDList, CausalLMOutput> model;
    private final Predictor<NDList, CausalLMOutput> predictor;
    private final NDManager manager;
    private final HuggingFaceTokenizer tokenizer;
    private final String searchName;
    private final SearchConfig config;
    private final TextGenerator generator;

    @SneakyThrows
    public Gpt2PyTorchChatBotService() {
        final Criteria<NDList, CausalLMOutput> criteria =
            Criteria.builder()
                .setTypes(NDList.class, CausalLMOutput.class)
                .optModelUrls("https://djl-misc.s3.amazonaws.com/test/models/gpt2/gpt2_pt.zip")
                .optEngine("PyTorch")
                .optTranslatorFactory(new DeferredTranslatorFactory())
                .build();

        this.model = criteria.loadModel();
        this.predictor = model.newPredictor();
        this.manager = model.getNDManager().newSubManager();
        this.tokenizer = HuggingFaceTokenizer.newInstance("gpt2");

        this.searchName = "beam"; // "greedy", "contrastive", "beam"
        config = new SearchConfig();
        config.setMaxSeqLength(60);
        if (searchName.equals("contrastive") || searchName.equals("beam")) {
            config.setPadTokenId(220L);
        }
        this.generator = new TextGenerator(predictor, searchName, config);
    }

    @PreDestroy
    @Override
    public void close() {
        model.close();
        predictor.close();
        tokenizer.close();
        manager.close();
    }

    @SneakyThrows
    @Override
    public Flux<String> createChatCompletion(@NotNull String message) {
        switch (searchName) {
            case "contrastive" -> {
                final NDArray inputIdArray = encodeWithPadding(manager, tokenizer, new String[] {message});

                final NDArray outputs = generator.generate(inputIdArray);
                return Flux.fromArray(decodeWithOffset(tokenizer, outputs, generator.getPositionOffset()));
            }
            case "beam" -> {
                final NDArray inputIdArray = encodeWithPadding(manager, tokenizer, new String[] {message});

                final NDArray outputs = generator.generate(inputIdArray);
                return Flux.just(
                    decodeWithOffset(tokenizer, outputs, generator.getPositionOffset().repeat(0, config.getBeam()))
                );
            }
            case "greedy" -> {
                final Encoding encoding = tokenizer.encode(message);
                final long[] inputIds = encoding.getIds();
                final NDArray inputIdArray = manager.create(inputIds).expandDims(0);

                final NDArray output = generator.generate(inputIdArray);
                final long[] outputIds = output.toLongArray();

                return Flux.just(tokenizer.decode(outputIds));
            }
            default -> {
                return Flux.empty();
            }
        }
    }

    private static NDArray encodeWithPadding(NDManager manager, HuggingFaceTokenizer tokenizer, String @NotNull [] inputs) {
        NDArray inputIdArray = null;
        for (String input : inputs) {
            long[] inputIds = tokenizer.encode(input).getIds();
            NDArray deltaInputIdArray = manager.create(inputIds).expandDims(0);
            if (inputIdArray == null) {
                inputIdArray = deltaInputIdArray;
            } else {
                if (inputIdArray.getShape().get(1) > deltaInputIdArray.getShape().get(1)) {
                    // pad deltaInputIdArray
                    long batchSize = deltaInputIdArray.getShape().get(0);
                    long deltaSeqLength = inputIdArray.getShape().get(1) - deltaInputIdArray.getShape().get(1);
                    deltaInputIdArray = manager.full(
                            new Shape(batchSize, deltaSeqLength),
                            220L,
                            DataType.INT64
                        ).concat(deltaInputIdArray, 1);
                } else if (inputIdArray.getShape().get(1) < deltaInputIdArray.getShape().get(1)) {
                    // pad inputIdArray
                    long batchSize = inputIdArray.getShape().get(0);
                    long deltaSeqLength = deltaInputIdArray.getShape().get(1) - inputIdArray.getShape().get(1);
                    inputIdArray =
                        manager.full(
                            new Shape(batchSize, deltaSeqLength),
                                220L,
                            DataType.INT64
                        ).concat(inputIdArray, 1);
                }
                inputIdArray = inputIdArray.concat(deltaInputIdArray, 0);
            }
        }
        return inputIdArray;
    }

    private static String @NotNull [] decodeWithOffset(HuggingFaceTokenizer tokenizer, @NotNull NDArray outputIds, NDArray offset) {
        long batchSize = outputIds.getShape().get(0);
        String[] outputs = new String[(int) batchSize];
        for (int i = 0; i < batchSize; i++) {
            long startIndex = offset.getLong(i);
            long[] outputId = outputIds.get("{},{}:", i, startIndex).toLongArray();
            outputs[i] = tokenizer.decode(outputId);
        }
        return outputs;
    }
}
