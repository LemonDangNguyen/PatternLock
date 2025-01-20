1. MainActivity
Chức Năng:
•	Giao Diện Chính: Là màn hình đầu tiên mà người dùng thấy khi mở ứng dụng.
•	Chức Năng: 
o	Nút "Tạo mới hình vẽ": Chuyển hướng người dùng đến CreatePatternActivity để tạo một mẫu hình mới.
o	Nút "Mở khóa": Chuyển hướng người dùng đến UnlockActivity để nhập mẫu hình đã lưu và mở khóa ứng dụng.
•	Quản Lý Giao Diện: 
o	Quan Sát savedPattern: Sử dụng LiveData từ PatternLockViewModel để xác định xem đã có mẫu hình được lưu hay chưa. Dựa vào đó, hiển thị hoặc ẩn các nút tương ứng.
Vai Trò:
•	Điểm khởi đầu của ứng dụng.
•	Quản lý điều hướng giữa các Activity dựa trên trạng thái mẫu hình đã lưu.
________________________________________
2. CreatePatternActivity
Chức Năng:
•	Tạo Mẫu Hình: Cho phép người dùng tạo và xác nhận một mẫu hình mới.
•	Quy Trình: 
1.	Vẽ Mẫu Hình Lần Đầu: Người dùng vẽ mẫu hình và ứng dụng lưu tạm thời mẫu hình này.
2.	Xác Nhận Mẫu Hình: Người dùng vẽ lại mẫu hình để xác nhận. Nếu khớp với mẫu hình đầu tiên, mẫu hình sẽ được lưu trữ vĩnh viễn.
Quản Lý Giao Diện:
•	PatternLockView: Custom View để người dùng vẽ mẫu hình.
•	statusText: TextView hiển thị hướng dẫn cho người dùng (ví dụ: "Vẽ lại mẫu hình để xác nhận").
Vai Trò:
•	Thu thập và lưu trữ mẫu hình mới từ người dùng.
•	Đảm bảo rằng mẫu hình được xác nhận chính xác trước khi lưu trữ.
________________________________________
3. UnlockActivity
Chức Năng:
•	Mở Khóa Ứng Dụng: Cho phép người dùng nhập mẫu hình đã lưu để mở khóa ứng dụng.
•	Quy Trình: 
1.	Nhập Mẫu Hình: Người dùng vẽ mẫu hình.
2.	Xác Thực: So sánh mẫu hình nhập với mẫu hình đã lưu. Nếu đúng, chuyển hướng sang UnlockSuccessActivity; nếu sai, hiển thị thông báo lỗi.
Quản Lý Giao Diện:
•	PatternLockView: Custom View để người dùng vẽ mẫu hình.
•	statusText: TextView hiển thị trạng thái (ví dụ: "Vẽ hình để mở khóa").
Vai Trò:
•	Xác thực mẫu hình người dùng nhập với mẫu hình đã lưu.
•	Điều hướng người dùng dựa trên kết quả xác thực (thành công hoặc thất bại).
________________________________________
4. UnlockSuccessActivity
Chức Năng:
•	Thông Báo Thành Công: Hiển thị thông báo khi người dùng mở khóa thành công.
•	Chức Năng Thêm: 
o	Nút "Thoát": Đóng toàn bộ ứng dụng.
o	Nút "Reset": Xóa mẫu hình đã lưu và quay lại MainActivity để bắt đầu quá trình từ đầu.
Quản Lý Giao Diện:
•	TextView: Hiển thị thông báo "Mở khóa thành công!".
•	Hai Nút: "Thoát" và "Reset" để thực hiện các chức năng tương ứng.
Vai Trò:
•	Thông báo cho người dùng biết rằng họ đã mở khóa thành công.
•	Cung cấp các tùy chọn tiếp theo như thoát ứng dụng hoặc reset mẫu hình.
________________________________________
5. PatternLockView
Chức Năng:
•	Custom View: Tạo một giao diện vẽ mẫu hình dạng mạng 3x3 (hoặc khác) cho ứng dụng Pattern Lock.
•	Các Thành Phần: 
o	Dots (Dot): Các chấm trong grid mà người dùng có thể kết nối để tạo mẫu hình.
o	Vẽ Đường Nối: Vẽ đường nối giữa các chấm được chọn.
o	Xử Lý Sự Kiện Chạm: Nhận diện các dot được chọn dựa trên vị trí chạm của người dùng.
Quản Lý Giao Diện:
•	Vẽ Dots: Sử dụng Canvas để vẽ các chấm.
•	Vẽ Đường Nối: Sử dụng Path và Paint để vẽ đường nối giữa các chấm được chọn.
•	Tinh Chỉnh Vị Trí: Điều chỉnh vị trí các dot để chúng hiển thị chính giữa view với khoảng cách gần nhau hơn.
Vai Trò:
•	Thu thập đầu vào từ người dùng về mẫu hình họ vẽ.
•	Gửi dữ liệu mẫu hình đã hoàn thành (LiveData) cho các Activity tương ứng để xử lý lưu trữ hoặc xác thực.
________________________________________
6. Dot (Data Class)
Chức Năng:
•	Mô Tả Một Dot: Đại diện cho một chấm trong grid Pattern Lock.
•	Các Thuộc Tính: 
o	x và y: Tọa độ của dot trên màn hình.
o	radius: Bán kính của dot.
o	id: Định danh duy nhất cho mỗi dot (thường từ 0 đến 8 cho grid 3x3).
Phương Thức:
•	contains(px: Float, py: Float): Kiểm tra xem một điểm (px, py) có nằm trong dot hay không.
Vai Trò:
•	Cung cấp thông tin về vị trí và kích thước của mỗi dot để PatternLockView có thể vẽ và nhận diện các dot được chọn.
________________________________________
7. PatternLockViewModel
Chức Năng:
•	Quản Lý Dữ Liệu: Giữ và quản lý dữ liệu mẫu hình đã lưu và trạng thái mở khóa.
•	Tương Tác với Repository: Sử dụng PatternRepository để lưu trữ và truy xuất mẫu hình từ SharedPreferences.
Các Thuộc Tính:
•	savedPattern: LiveData chứa danh sách ID của các dot đã được lưu trữ.
•	unlockStatus: LiveData chứa trạng thái mở khóa (true nếu thành công, false nếu thất bại).
Các Phương Thức:
•	savePattern(pattern: List<Int>): Lưu mẫu hình vào repository.
•	validatePattern(inputPattern: List<Int>): Xác thực mẫu hình nhập vào với mẫu hình đã lưu.
•	clearPattern(): Xóa mẫu hình đã lưu từ repository.
Vai Trò:
•	Là cầu nối giữa giao diện người dùng và dữ liệu mẫu hình.
•	Cung cấp dữ liệu và trạng thái cần thiết cho các Activity thông qua LiveData.
________________________________________
8. PatternRepository
Chức Năng:
•	Quản Lý Lưu Trữ: Lưu trữ và truy xuất mẫu hình sử dụng SharedPreferences.
•	Singleton: Đảm bảo rằng chỉ có một instance của repository tồn tại trong toàn bộ ứng dụng, giúp chia sẻ dữ liệu giữa các Activity.
Các Thuộc Tính:
•	savedPattern: LiveData chứa mẫu hình đã lưu.
Các Phương Thức:
•	savePattern(pattern: List<Int>): Lưu mẫu hình dưới dạng chuỗi vào SharedPreferences và cập nhật LiveData.
•	getPattern(): Truy xuất mẫu hình đã lưu từ SharedPreferences.
•	clearPattern(): Xóa mẫu hình đã lưu khỏi SharedPreferences và cập nhật LiveData.
Vai Trò:
•	Cung cấp một nguồn dữ liệu đáng tin cậy để lưu trữ và truy xuất mẫu hình.
•	Chia sẻ dữ liệu giữa các Activity thông qua ViewModel.
________________________________________
9. UnlockSuccessActivity
Chức Năng:
•	Thông Báo Thành Công: Hiển thị thông báo cho người dùng biết rằng họ đã mở khóa thành công.
•	Chức Năng Thêm: 
o	Nút "Thoát": Đóng toàn bộ ứng dụng.
o	Nút "Reset": Xóa mẫu hình đã lưu và quay lại MainActivity để bắt đầu lại quá trình từ đầu.
Quản Lý Giao Diện:
•	TextView: Hiển thị thông báo "Mở khóa thành công!".
•	Hai Nút: "Thoát" và "Reset" để thực hiện các chức năng tương ứng.
Vai Trò:
•	Thông báo kết quả mở khóa.
•	Cung cấp các tùy chọn tiếp theo cho người dùng (thoát hoặc reset).
________________________________________
10. Tổng Kết Luồng Hoạt Động
1.	Mở Ứng Dụng:
o	MainActivity hiển thị nút "Tạo mới hình vẽ" (nếu chưa có mẫu hình) hoặc nút "Mở khóa" (nếu đã có mẫu hình).
2.	Tạo Mẫu Hình:
o	Người dùng nhấn "Tạo mới hình vẽ" để mở CreatePatternActivity.
o	Trong CreatePatternActivity, người dùng vẽ mẫu hình lần đầu và sau đó xác nhận lại mẫu hình.
o	Nếu xác nhận thành công, mẫu hình được lưu thông qua PatternLockViewModel và PatternRepository.
o	Sau khi lưu thành công, người dùng quay lại MainActivity và nút "Mở khóa" được hiển thị.
3.	Mở Khóa:
o	Người dùng nhấn "Mở khóa" để mở UnlockActivity.
o	Trong UnlockActivity, người dùng vẽ mẫu hình để mở khóa.
o	Mẫu hình được xác thực thông qua PatternLockViewModel và PatternRepository.
o	Nếu mở khóa thành công, người dùng chuyển đến UnlockSuccessActivity; nếu thất bại, hiển thị thông báo lỗi.
4.	Sau Khi Mở Khóa Thành Công:
o	Trong UnlockSuccessActivity, người dùng có thể: 
	Nhấn "Thoát" để đóng toàn bộ ứng dụng.
	Nhấn "Reset" để xóa mẫu hình đã lưu và quay lại MainActivity để bắt đầu lại quá trình từ đầu.
Kết Luận
Pattern Lock với các chức năng cơ bản như tạo mẫu hình, mở khóa và reset mẫu hình. Bằng cách sử dụng kiến trúc MVVM với ViewModel và Repository, bạn đã tạo ra một luồng dữ liệu mạnh mẽ và dễ bảo trì.
Tóm lại:
•	MainActivity quản lý điều hướng và hiển thị các nút tùy thuộc vào trạng thái mẫu hình.
•	CreatePatternActivity cho phép người dùng tạo và xác nhận mẫu hình mới.
•	UnlockActivity cho phép người dùng nhập mẫu hình để mở khóa.
•	UnlockSuccessActivity thông báo thành công và cung cấp các tùy chọn tiếp theo.
•	PatternLockView là custom View để vẽ và nhận diện mẫu hình.
•	Dot là data class đại diện cho mỗi chấm trong grid.
•	PatternLockViewModel quản lý dữ liệu mẫu hình và xác thực mở khóa.
•	PatternRepository quản lý lưu trữ và truy xuất mẫu hình từ SharedPreferences.

