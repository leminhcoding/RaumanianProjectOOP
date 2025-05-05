import json
import numpy as np
import sys
import torch
from sentence_transformers import SentenceTransformer, util

# ----- 1. Load truy vấn -----
if len(sys.argv) < 2:
    print("⚠️ Không có truy vấn được truyền từ Java.")
    sys.exit(1)

query = sys.argv[1]
print(f"📥 Truy vấn từ người dùng: {query}")

# ----- 2. Load dữ liệu -----
with open("src/main/java/resources/product_texts.json", "r", encoding="utf-8") as f:
    product_texts = json.load(f)

embeddings_np = np.load("src/main/java/resources/product_embeddings.npy")
embeddings = torch.tensor(embeddings_np, dtype=torch.float32)

# ----- 3. Nhúng truy vấn -----
model = SentenceTransformer("all-MiniLM-L6-v2")
query_embedding = model.encode(query, convert_to_tensor=True)

# ----- 4. Tính tương đồng -----
scores = util.cos_sim(query_embedding, embeddings)[0]
scores_np = scores.cpu().numpy()

# ----- 5. Lọc & sắp xếp -----
results = []
for idx, score in enumerate(scores_np):
    if score >= 0.3:  # ⚠️ Ngưỡng lọc tương đồng
        product = product_texts[idx]
        product["score"] = round(float(score), 4)
        results.append(product)

# Sắp xếp giảm dần theo điểm
results.sort(key=lambda x: x["score"], reverse=True)

# ----- 6. Lưu kết quả -----
with open("src/main/java/resources/top_results.json", "w", encoding="utf-8") as f:
    json.dump(results, f, ensure_ascii=False, indent=2)

print(f"✅ Đã lưu {len(results)} kết quả liên quan vào top_results.json")
