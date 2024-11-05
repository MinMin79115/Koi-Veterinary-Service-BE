# Koi Veterinary API

Đây là API backend cho ứng dụng Koi Veterinary, một hệ thống quản lý phòng khám thú y.

## Tính năng

- Xác thực và ủy quyền người dùng
- Quản lý tài khoản người dùng (khách hàng, nhân viên, bác sĩ thú y)
- Quản lý dịch vụ
- Đặt lịch hẹn
- Thanh toán qua VNPay
- Gửi email
- Quản lý phản hồi từ khách hàng

## Công nghệ sử dụng

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- MySQL
- JWT cho xác thực
- Maven

## Cài đặt

1. Clone repository này
2. Cấu hình cơ sở dữ liệu MySQL trong `application.yaml`
3. Chạy ứng dụng bằng lệnh: `mvn spring-boot:run`

## API Endpoints

### Xác thực

- `POST /api/v1/auth/login`: Đăng nhập
- `POST /api/v1/auth/register`: Đăng ký tài khoản mới
- `POST /api/v1/auth/addstaff`: Thêm nhân viên mới (yêu cầu quyền Admin)

### Quản lý người dùng

- `GET /api/v1/customers`: Lấy danh sách tài khoản
- `GET /api/v1/customers/{userId}`: Lấy thông tin người dùng
- `PUT /api/v1/customers/{userId}`: Cập nhật thông tin người dùng
- `DELETE /api/v1/customers/{userId}`: Xóa tài khoản người dùng

### Quản lý dịch vụ

- `POST /api/v1/services`: Tạo dịch vụ mới
- `GET /api/v1/services`: Lấy danh sách dịch vụ
- `PUT /api/v1/services/{serviceId}`: Cập nhật dịch vụ
- `DELETE /api/v1/services/{serviceId}`: Xóa dịch vụ

### Đặt lịch hẹn

- `POST /api/v1/bookings`: Tạo lịch hẹn mới
- `GET /api/v1/bookings`: Lấy danh sách lịch hẹn
- `PUT /api/v1/bookings/{bookingId}`: Cập nhật trạng thái lịch hẹn
- `PUT /api/v1/bookings/delete/{bookingId}`: Hủy lịch hẹn

### Thanh toán

- `GET /api/v1/payment/vnpay`: Tạo URL thanh toán VNPay
- `GET /api/v1/payment/vnpayback`: Xử lý callback từ VNPay

### Gửi email

- `POST /api/v1/mail/send/{mail}`: Gửi email

### Quản lý phản hồi

- `POST /api/v1/feedback`: Tạo phản hồi mới
- `GET /api/v1/feedback`: Lấy danh sách phản hồi
- `GET /api/v1/feedback/{feedbackId}`: Lấy thông tin phản hồi theo ID
- `PUT /api/v1/feedback/{feedbackId}`: Cập nhật phản hồi
- `DELETE /api/v1/feedback/{feedbackId}`: Xóa phản hồi

## Cấu hình

Các cấu hình chính của ứng dụng nằm trong file `application.yaml`. Bạn cần cấu hình:

- Kết nối cơ sở dữ liệu MySQL
- Thông tin SMTP để gửi email
- Thông tin tích hợp VNPay

## Bảo mật

Ứng dụng sử dụng JWT để xác thực. Mọi request đến các endpoint được bảo vệ cần có token JWT hợp lệ trong header `Authorization`.
