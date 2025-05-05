import json
import numpy as np
from sentence_transformers import SentenceTransformer, util

# Load dữ liệu
with open("product_texts.json", "r", encoding="utf-8") as f:
    product_texts = json.load(f)
embeddings = np.load("product_embeddings.npy")

# Load model
model = SentenceTransformer("all-MiniLM-L6-v2")

def search(query, top_k=10):
    query_embedding = model.encode(query, convert_to_tensor=True)
    scores = util.cos_sim(query_embedding, embeddings)[0]
    top_results = np.argpartition(-scores, range(top_k))[:top_k]

    results = []
    for idx in top_results:
        product = product_texts[idx]
        product["score"] = float(scores[idx])
        results.append(product)

    return sorted(results, key=lambda x: x["score"], reverse=True)

# Truy vấn đầu vào
query = input("🔍 Nhập truy vấn tìm kiếm: ")
results = search(query)

# Xuất ra file top_results.json
with open("top_results.json", "w", encoding="utf-8") as f:
    json.dump(results, f, ensure_ascii=False, indent=2)

print("✅ Đã xuất kết quả ra top_results.json")
