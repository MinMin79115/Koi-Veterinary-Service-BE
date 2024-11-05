DROP DATABASE IF EXISTS koiveterian;
CREATE DATABASE KoiVeterian;

USE KoiVeterian;

-- Bảng hiện có
CREATE TABLE user_account (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    address VARCHAR(255),
    email VARCHAR(100) NOT NULL UNIQUE,
    fullname NVARCHAR(255),
    role ENUM('ADMIN', 'STAFF', 'CUSTOMER', 'MANAGER', 'VETERINARIAN') NOT NULL
);

CREATE TABLE services (
    service_id INT AUTO_INCREMENT PRIMARY KEY,
    service_name NVARCHAR(100) NOT NULL,
    service_description NVARCHAR(500)
);

CREATE TABLE service_type (
    service_type_id INT AUTO_INCREMENT PRIMARY KEY,
    service_type ENUM('Online', 'At_Center', 'At_Home') NOT NULL,
    service_price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE services_detail (
    services_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    service_id INT NOT NULL,
    service_type_id INT NOT NULL,
    FOREIGN KEY (service_id) REFERENCES services(service_id),
    FOREIGN KEY (service_type_id) REFERENCES service_type(service_type_id)
);

CREATE TABLE veterinarian (
    veterinarian_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    service_type_id INT,
    state ENUM('ONLINE', 'OFFLINE', 'WORKING') NOT NULL DEFAULT 'OFFLINE',
    FOREIGN KEY (service_type_id) REFERENCES service_type(service_type_id),
    FOREIGN KEY (user_id) REFERENCES user_account(user_id)
);

CREATE TABLE time_slot (
    slot_time_id INT AUTO_INCREMENT PRIMARY KEY,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);

CREATE TABLE veterinarian_time_slot (
    slot_id INT AUTO_INCREMENT PRIMARY KEY,
    slot_time_id INT NOT NULL,
    veterinarian_id INT NOT NULL,
    slot_status ENUM('AVAILABLE', 'UNAVAILABLE' , 'DELETED') NOT NULL,
    FOREIGN KEY (slot_time_id) REFERENCES time_slot(slot_time_id),
    FOREIGN KEY (veterinarian_id) REFERENCES veterinarian(veterinarian_id)
);

CREATE TABLE booking (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    services_detail_id INT NOT NULL,
    veterinarian_id INT,
    slot_id INT,
    booking_time DATETIME,
    service_time DATETIME,
    note varchar(255),
    status ENUM('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED') NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (user_id) REFERENCES user_account(user_id),
    FOREIGN KEY (services_detail_id) REFERENCES services_detail(services_detail_id),
    FOREIGN KEY (veterinarian_id) REFERENCES veterinarian(veterinarian_id),
    FOREIGN KEY (slot_id) REFERENCES veterinarian_time_slot(slot_id)
);

CREATE TABLE bill (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_status ENUM('PENDING', 'PAID', 'FAILED') NOT NULL DEFAULT 'PENDING',
    payment_method VARCHAR(50),
    payment_date DATETIME,
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id)
);

CREATE TABLE feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    rating INT,
    feedback varchar(255),
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id)
);

INSERT INTO user_account (username, password, phone, address, email, fullname, role) VALUES
('thuannguyen', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0123456789', 'Đại học FPT, TP.HCM', 'thuannguyen@gmail.com', 'Nguyễn Minh Thuận', 'ADMIN'),
('dungpham', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0987654321', 'Đại học FPT, TP.HCM', 'dungpham@gmail.com', 'Phạm Hùng Dũng', 'VETERINARIAN'),
('khanghuynh', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0369852147', 'Đại học FPT, TP.HCM', 'khanghuynh@gmail.com', 'Huỳnh Quốc Khang', 'STAFF'),
('minhpham', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0369852147', 'Đại học FPT, TP.HCM', 'minhpham@gmail.com', 'Phạm Hữu Nhật Minh', 'CUSTOMER'),
('nguyenvanan', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0912345678', 'Đại học Quốc gia, Hà Nội', 'nguyenvanan@gmail.com', 'Nguyễn Văn An', 'VETERINARIAN'),
('tranthimai', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0923456789', 'Đại học Bách Khoa, Đà Nẵng', 'tranthimai@gmail.com', 'Trần Thị Mai', 'VETERINARIAN'),
('leminhtuan', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0934567890', 'Đại học Sư phạm, TP.HCM', 'leminhtuan@gmail.com', 'Lê Minh Tuấn', 'VETERINARIAN'),
('phamthihuong', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0945678901', 'Đại học Kinh tế, Hà Nội', 'phamthihuong@gmail.com', 'Phạm Thị Hương', 'VETERINARIAN'),
('dovanbinh', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0956789012', 'Đại học Công nghệ, TP.HCM', 'dovanbinh@gmail.com', 'Đỗ Văn Bình', 'VETERINARIAN'),
('buithilan', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0967890123', 'Đại học Ngoại thương, Hà Nội', 'buithilan@gmail.com', 'Bùi Thị Lan', 'VETERINARIAN'),
('hoangvannam', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0978901234', 'Đại học Y Dược, TP.HCM', 'hoangvannam@gmail.com', 'Hoàng Văn Nam', 'VETERINARIAN'),
('vuthihoa', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0989012345', 'Đại học Mỹ thuật, Hà Nội', 'vuthihoa@gmail.com', 'Vũ Thị Hoa', 'VETERINARIAN'),
('nguyentheduy', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0990123456', 'Đại học Khoa học tự nhiên, TP.HCM', 'nguyentheduy@gmail.com', 'Nguyễn Thế Duy', 'VETERINARIAN'),
('tranvanhoa', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0901234567', 'Đại học Luật, Hà Nội', 'tranvanhoa@gmail.com', 'Trần Văn Hòa', 'VETERINARIAN'),
('nguyenhuy', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0912345670', 'Đại học Bách Khoa, TP.HCM', 'nguyenhuy@gmail.com', 'Nguyễn Huy', 'CUSTOMER'),
('tranthanh', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0912345671', 'Đại học Sư phạm, Hà Nội', 'tranthanh@gmail.com', 'Trần Thành', 'CUSTOMER'),
('lethanh', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0912345672', 'Đại học Kinh tế, Đà Nẵng', 'lethanh@gmail.com', 'Lê Thành', 'CUSTOMER'),
('phamvan', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0912345673', 'Đại học Công nghệ, TP.HCM', 'phamvan@gmail.com', 'Phạm Văn', 'CUSTOMER'),
('hoanganh', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0912345674', 'Đại học Y Dược, Hà Nội', 'hoanganh@gmail.com', 'Hoàng Anh', 'CUSTOMER'),
('nguyentuan', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0912345675', 'Đại học Ngoại thương, TP.HCM', 'nguyentuan@gmail.com', 'Nguyễn Tuấn', 'CUSTOMER'),
('tranminh', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0912345676', 'Đại học Luật, Đà Nẵng', 'tranminh@gmail.com', 'Trần Minh', 'CUSTOMER'),
('lethuy', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0912345677', 'Đại học Khoa học tự nhiên, TP.HCM', 'lethuy@gmail.com', 'Lê Thúy', 'CUSTOMER'),
('nguyenvan', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0912345678', 'Đại học Quốc gia, Hà Nội', 'nguyenvan@gmail.com', 'Nguyễn Văn', 'CUSTOMER'),
('phamthanh', '$2a$12$v7/zm1eAsYaJ.IXmjijik.CSD9sbkhDRfKnMrLKOX1Rwm71IPRPMe', '0912345679', 'Đại học Bách Khoa, TP.HCM', 'phamthanh@gmail.com', 'Phạm Thành', 'CUSTOMER');

INSERT INTO services (service_name, service_description) VALUES
('Koi fish disease treatment', 'Order fish treatment and prescribe medication'),
('Evaluate Koi fish pond quality', 'Make an appointment for a veterinarian to come to your home to assess the quality of your Koi pond and advise on improving your pond to meet standards.'),
('Online Consulting', 'Book an online consultation with a veterinarian for advice');

INSERT INTO service_type (service_type, service_price) VALUES
('Online', 50000),
('At_Center', 150000),
('At_Home', 200000);

INSERT INTO services_detail (service_id, service_type_id) VALUES
(1, 2),
(1, 3),
(2, 3),
(3, 1);

INSERT INTO time_slot (start_time, end_time) VALUES
('07:00:00', '09:00:00'),
('09:00:00', '11:00:00'),
('13:00:00', '15:00:00'),
('15:00:00', '17:00:00');

INSERT INTO veterinarian (user_id, service_type_id, state) VALUES
(2, 1, 'ONLINE'),  -- Dung Pham
(5, 1, 'ONLINE'),  -- Nguyen Van An
(6, 1, 'ONLINE'),  -- Tran Thi Mai
(7, null, 'OFFLINE'),  -- Le Minh Tuan
(8, null, 'OFFLINE'),  -- Pham Thi Huong
(9, null, 'OFFLINE'),  -- Do Van Binh
(10, null, 'OFFLINE'), -- Bui Thi Lan
(11, null, 'OFFLINE'), -- Hoang Van Nam
(12, null, 'OFFLINE'), -- Vu Thi Hoa
(13, null, 'OFFLINE'), -- Nguyen The Duy
(14, null, 'OFFLINE'); -- Tran Van Hoa

INSERT INTO veterinarian_time_slot (slot_time_id, veterinarian_id, slot_status) VALUES
(1, 4, 'AVAILABLE'),  -- Le Minh Tuan
(1, 5, 'AVAILABLE'),  -- Pham Thi Huong
(1, 6, 'AVAILABLE'),  -- Do Van Binh
(1, 7, 'AVAILABLE'), -- Bui Thi Lan
(1, 8, 'AVAILABLE'), -- Hoang Van Nam
(1, 9, 'AVAILABLE'), -- Vu Thi Hoa
(1, 10, 'AVAILABLE'), -- Nguyen The Duy
(1, 11, 'AVAILABLE'), -- Tran Van Hoa

(2, 4, 'AVAILABLE'),  -- Le Minh Tuan
(2, 5, 'AVAILABLE'),  -- Pham Thi Huong
(2, 6, 'AVAILABLE'),  -- Do Van Binh
(2, 7, 'AVAILABLE'), -- Bui Thi Lan
(2, 8, 'AVAILABLE'), -- Hoang Van Nam
(2, 9, 'AVAILABLE'), -- Vu Thi Hoa
(2, 10, 'AVAILABLE'), -- Nguyen The Duy
(2, 11, 'AVAILABLE'), -- Tran Van Hoa

(3, 4, 'AVAILABLE'),  -- Le Minh Tuan
(3, 5, 'AVAILABLE'),  -- Pham Thi Huong
(3, 6, 'AVAILABLE'),  -- Do Van Binh
(3, 7, 'AVAILABLE'), -- Bui Thi Lan
(3, 8, 'AVAILABLE'), -- Hoang Van Nam
(3, 9, 'AVAILABLE'), -- Vu Thi Hoa
(3, 10, 'AVAILABLE'), -- Nguyen The Duy
(3, 11, 'AVAILABLE'), -- Tran Van Hoa

(4, 4, 'AVAILABLE'),  -- Le Minh Tuan
(4, 5, 'AVAILABLE'),  -- Pham Thi Huong
(4, 6, 'AVAILABLE'),  -- Do Van Binh
(4, 7, 'AVAILABLE'), -- Bui Thi Lan
(4, 8, 'AVAILABLE'), -- Hoang Van Nam
(4, 9, 'AVAILABLE'), -- Vu Thi Hoa
(4, 10, 'AVAILABLE'), -- Nguyen The Duy
(4, 11, 'AVAILABLE'); -- Tran Van Hoa

INSERT INTO booking (user_id, services_detail_id, veterinarian_id, slot_id, booking_time, service_time, note, status) VALUES
(4, 1, 2, 1, '2024-11-01 14:31:09', '2024-11-15 10:00:00', 'Yêu cầu điều trị bệnh cho cá Koi', 'COMPLETED'),
(15, 3, 6, 3, '2024-11-01 11:45:00', '2024-11-17 14:00:00', 'Tư vấn trực tuyến', 'COMPLETED'),
(5, 1, 2, 1, '2024-11-03 10:00:00', '2024-11-18 09:00:00', 'Yêu cầu điều trị bệnh cho cá Koi', 'COMPLETED'),  -- Khang Huynh
(6, 2, 5, 2, '2024-11-05 12:30:00', '2024-11-19 10:30:00', 'Đánh giá chất lượng ao Koi', 'COMPLETED'),  -- Tran Thi Mai
(7, 3, 6, 3, '2024-11-05 14:00:00', '2024-11-20 11:00:00', 'Tư vấn trực tuyến', 'COMPLETED'),  -- Le Minh Tuan
(8, 2, 2, 1, '2024-11-07 15:00:00', '2024-11-21 12:00:00', 'Yêu cầu điều trị bệnh cho cá Koi', 'COMPLETED'),  -- Pham Thi Huong
(9, 2, 5, 2, '2024-11-09 16:00:00', '2024-11-22 13:00:00', 'Tư vấn trực tuyến', 'COMPLETED'),  -- Do Van Binh
(10, 3, 6, 3, '2024-11-09 17:00:00', '2024-11-23 14:00:00', 'Tư vấn trực tuyến', 'COMPLETED'),  -- Bui Thi Lan
(11, 4, 2, 1, '2024-11-10 18:00:00', '2024-11-24 15:00:00', 'Yêu cầu điều trị bệnh cho cá Koi', 'COMPLETED'),  -- Hoang Van Nam
(12, 2, 5, 2, '2024-11-12 19:00:00', '2024-11-25 16:00:00', 'Đánh giá chất lượng ao Koi', 'COMPLETED'),  -- Vu Thi Hoa
(13, 1, 6, 3, '2024-11-12 20:00:00', '2024-11-26 17:00:00', 'Tư vấn trực tuyến', 'COMPLETED'),  -- Nguyen The Duy
(4, 4, 2, 1, '2024-11-15 21:00:00', '2024-11-27 18:00:00', 'Yêu cầu điều trị bệnh cho cá Koi', 'COMPLETED');  -- Minh Pham

-- Thêm dữ liệu vào bảng bill
INSERT INTO bill (booking_id, amount, payment_status, payment_method, payment_date) VALUES
(1, 150000, 'PAID', 'Credit Card', '2024-11-15 10:00:00'),
(2, 200000, 'PAID', 'Cash', '2024-11-16 11:00:00'),
(3, 50000, 'PAID', 'Online Payment', '2024-11-17 14:00:00'),
(4, 150000, 'PAID', 'Credit Card', '2024-11-18 09:00:00'),
(5, 200000, 'PAID', 'Cash', '2024-11-19 10:30:00'),
(6, 50000, 'PAID', 'Online Payment', '2024-11-20 11:00:00'),
(7, 150000, 'PAID', 'Credit Card', '2024-11-21 12:00:00'),
(8, 200000, 'PAID', 'Cash', '2024-11-22 13:00:00'),
(9, 50000, 'PAID', 'Online Payment', '2024-11-23 14:00:00'),
(10, 150000, 'PAID', 'Credit Card', '2024-11-24 15:00:00'),
(11, 200000, 'PAID', 'Cash', '2024-11-25 16:00:00'),
(12, 50000, 'PAID', 'Online Payment', '2024-11-26 17:00:00');

-- Thêm dữ liệu vào bảng feedback
INSERT INTO feedback (booking_id, rating, feedback) VALUES
(1, 5, 'Dịch vụ rất tốt, bác sĩ rất tận tình!'),
(2, 4, 'Đánh giá chất lượng ao Koi rất hữu ích.'),
(3, 5, 'Tư vấn trực tuyến rất nhanh chóng và hiệu quả.'),
(4, 5, 'Dịch vụ điều trị rất hiệu quả!'),
(5, 4, 'Đánh giá chất lượng ao Koi rất chi tiết.'),
(6, 5, 'Tư vấn trực tuyến rất hữu ích!'),
(7, 5, 'Bác sĩ rất chuyên nghiệp!'),
(8, 4, 'Dịch vụ tốt nhưng cần cải thiện thời gian chờ.'),
(9, 5, 'Rất hài lòng với dịch vụ!'),
(10, 4, 'Dịch vụ tốt, sẽ quay lại!'),
(11, 5, 'Bác sĩ rất tận tâm!'),
(12, 4, 'Dịch vụ tốt, nhưng giá hơi cao.');
