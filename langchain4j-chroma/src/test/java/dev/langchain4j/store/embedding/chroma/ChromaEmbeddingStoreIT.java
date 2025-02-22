package dev.langchain4j.store.embedding.chroma;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIT;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static dev.langchain4j.internal.Utils.randomUUID;

@Testcontainers
class ChromaEmbeddingStoreIT extends EmbeddingStoreIT {

    @Container
    private static final GenericContainer<?> chroma = new GenericContainer<>("ghcr.io/chroma-core/chroma:0.4.6")
            .withExposedPorts(8000);

    EmbeddingStore<TextSegment> embeddingStore = ChromaEmbeddingStore.builder()
            .baseUrl(String.format("http://%s:%d", chroma.getHost(), chroma.getMappedPort(8000)))
            .collectionName(randomUUID())
            .build();

    EmbeddingModel embeddingModel = new AllMiniLmL6V2QuantizedEmbeddingModel();

    @Override
    protected EmbeddingStore<TextSegment> embeddingStore() {
        return embeddingStore;
    }

    @Override
    protected EmbeddingModel embeddingModel() {
        return embeddingModel;
    }
}