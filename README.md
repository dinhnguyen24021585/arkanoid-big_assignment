# 🧱 Arkanoid Game - Object-Oriented Programming Project

---
## 📌 Tác giả

| STT | Họ và tên | MSSV |
| :---: | :--- | :---: |
| 1 | Bùi Đình Nguyên | 24021585 |
| 2 | Lê Thu Huyền | 24021522 |
| 3 | Nguyễn Hồng Thảo Nguyên | 24021586 |

* **Giảng viên:** Kiều Văn Tuyên
* **Học kỳ:** HK1 - 2025

---
## 🚀 Mô tả Dự án

Đây là một phiên bản game Arkanoid cổ điển được phát triển bằng **Java** như là một dự án cuối khóa cho môn học Lập trình Hướng đối tượng (OOP). Dự án trình bày việc triển khai các nguyên tắc và mẫu thiết kế OOP.

### 🌟 Tính năng nổi bật 

1.  **Công nghệ:** Java 17+ và JavaFX/Swing.
2.  **Nguyên lý OOP:** Encapsulation, Inheritance, Polymorphism, Abstraction.
3.  **Mẫu thiết kế:** Singleton, Factory Method.
4.  **Trải nghiệm:** Bao gồm sound effects, animations, và power-up systems.
5.  **Tiện ích:** Hỗ trợ Save/Load game

### **🎮 Cơ chế**

* **Điều khiển thanh đỡ:** Điều khiển thanh đỡ (paddle) để nảy bóng và phá hủy các viên gạch (bricks).
* **Vật phẩm hỗ trợ:** Thu thập các vật phẩm tăng sức mạnh (power-ups) để nhận các khả năng đặc biệt.
* **Tiến trình:** Vượt qua nhiều cấp độ với độ khó tăng dần.
* **Điểm số:** Ghi điểm và cạnh tranh với high score.

---
## **📊 Sơ đồ UML**

### **Sơ đồ Lớp (Class Diagram)**



---
## **⚙️ Triển khai Mẫu thiết kế**

*Mô tả cách các mẫu thiết kế được áp dụng trong dự án.*

### **1. Singleton Pattern**

* **Áp dụng tại:** `GameManager`, `AudioManager`, `ResourceLoader`
* **Mục đích:** Đảm bảo chỉ có duy nhất một instance của lớp tồn tại trong suốt ứng dụng để quản lý tài nguyên hoặc trạng thái chung.


### **2. Factory Method Pattern**

* **Áp dụng tại:** `PowerUpFactory`, `BrickFactory`
* **Mục đích:** Cung cấp một giao diện để tạo ra các đối tượng trong một siêu lớp (superclass), nhưng cho phép các lớp con (subclass) thay đổi loại đối tượng sẽ được tạo. (Ví dụ: tạo ra các loại gạch/power-up khác nhau).

---

## **🛠️ Installation**

1.  Clone dự án từ repository.
2.  Mở dự án trong IDE.
3.  Build và Run project.

---
## **🕹️ Usage**

### **Controls**
Dùng chuột

### **Cách chơi**

1.  **Bắt đầu game:** Nhấn "New Game" từ menu chính.
2.  **Điều khiển thanh đỡ:** Dùng chuột di chuyển thanh đỡ.
3.  **Tung bóng:** Bóng chạm vào thanh đỡ tự nảy lên.
4.  **Phá hủy gạch:** Nảy bóng vào gạch để phá hủy chúng.
5.  **Thu thập Power-ups:** Bắt các vật phẩm rơi xuống để nhận khả năng đặc biệt.
6.  **Tránh mất bóng:** Giữ bóng không rơi ra ngoài phía dưới thanh đỡ.
7.  **Hoàn thành cấp độ:** Phá hủy tất cả gạch có thể phá hủy để chuyển sang cấp độ tiếp theo.

### **Power-ups**

| Icon | Name | Effect |
| :---: | :--- | :--- |
| 🟦 | **Expand Paddle** | Tăng chiều rộng thanh đỡ|
| ⚡ | **Fast Ball** | Tăng tốc độ bóng lên 50% |
| 🌀 | **Multi Ball** | Tạo thêm 2 quả bóng phụ |
| 💖 | **Heart** | Tăng thêm **+1 Mạng (life)** cho người chơi. |
| 🌀 | **Portal** | Dịch chuyển ngẫu nhiên bóng ra một trong bốn góc. |
| ↔️ | **Reverse Control** | **Đảo ngược** hướng điều khiển của thanh đỡ. |
| 🔫 | **Shooting** | Thanh đỡ **bắn đạn** để phá gạch. |

### **Hệ thống tính điểm**

* **Gạch thường (Normal Brick):** 10 điểm
* **Gạch cứng (Strong Brick):** 20 hoặc 30 điểm (tuỳ vào số lần để phá)
* **Gạch nổ (Explosive Brick):** 10 điểm + điểm từ các gạch lân cận bị phá hủy

---
## **🖼️ Demo**

### **Screenshots**

* **Main Menu:**
  
    ![Menu](https://github.com/dinhnguyen24021585/arkanoid-big_assignment/blob/74e09e5f56ad70df0d10071b154279e85b28a23f/menu.png)
* **Gameplay:**
  
    ![Gameplay](https://github.com/dinhnguyen24021585/arkanoid-big_assignment/blob/74e09e5f56ad70df0d10071b154279e85b28a23f/gameplay.png)
* **Power-ups in Action:**
  
    ![Power-ups](https://github.com/dinhnguyen24021585/arkanoid-big_assignment/blob/74e09e5f56ad70df0d10071b154279e85b28a23f/powerup.png)


### **Video**

* **Video Gameplay:**
    [![Video Demo]()]()


---
## **🔮 Cải tiến trong tương lai**

### **1. Các Chế độ chơi bổ sung**

* Chế độ **Time Attack** (Thử thách thời gian).
* Chế độ **Survival** (Sinh tồn) với các cấp độ không giới hạn.
* Chế độ **Co-op Multiplayer** (Chơi hợp tác nhiều người).

### **2. Cải thiện Gameplay**

* Thêm các trận **Boss Battles** ở cuối mỗi "thế giới".
* Bổ sung đa dạng Power-ups mới (Ví dụ: đóng băng thời gian, tạo tường chắn bảo vệ, v.v.).
* Triển khai **Hệ thống thành tích (Achievements system)**.

### **3. Cải tiến Kỹ thuật**

* Chuyển đổi sang thư viện **LibGDX** hoặc **JavaFX** để nâng cao chất lượng đồ họa.
* Thêm hiệu ứng hạt (**particle effects**) và các hoạt ảnh nâng cao.
* Triển khai chế độ đối thủ AI (AI opponent mode).
* Thêm bảng xếp hạng trực tuyến (**online leaderboard**) sử dụng database backend.

---


## **💻 Công nghệ sử dụng**

| Technology | Version | Purpose |
| :--- | :--- | :--- |
| **Java** | 17+ | Ngôn ngữ lập trình cốt lõi |
| **JavaFX** | 19.0.2 | Framework cho giao diện người dùng (GUI) |
| **Maven** | 3.9+ | Công cụ Build dự án |
| **Jackson** | 2.15.0 | Xử lý dữ liệu JSON |

---
## **⚖️ License & Ethics**

Dự án này được phát triển **chỉ với mục đích giáo dục**.

* **Academic Integrity:** Mã nguồn này được cung cấp như một tài liệu tham khảo. Vui lòng tuân thủ các chính sách về tính chính trực học thuật của tổ chức của bạn.

---
## **📝 Notes**

* Trò chơi được phát triển trong khuôn khổ chương trình giảng dạy của môn **Lập trình Hướng đối tượng (Object-Oriented Programming)** với Java.
* Toàn bộ mã nguồn được viết bởi các thành viên trong nhóm với sự hướng dẫn của giảng viên.
* Một số tài nguyên (hình ảnh, âm thanh) có thể được sử dụng cho mục đích giáo dục theo chính sách sử dụng hợp lý.
* Dự án này thể hiện ứng dụng thực tế các khái niệm OOP và các mẫu thiết kế.
---

*Cập nhật lần cuối: 12/11/2025*
