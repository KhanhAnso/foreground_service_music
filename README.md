- Sử dụng broadcast receiver để gửi action 

- Sử dụng LocalBroadCastManager thì nó sẽ tối ưu, tránh rò rỉ dữ liệu ra ngoài các ứng dụng khác và chỉ ứng dụng của mình mới nhận được dữ liệu.

- Lưu ý những cái key của intent mà dùng nhiều ta nên để nó trong một file Containst.java

1.Trong MyService

- Xử lý gửi action qua activity

![image](https://user-images.githubusercontent.com/65121835/185862947-ae5ce7e3-a1b3-42fe-b763-7562a51394d2.png)

- Khi có sự thay đổi về action trong service nó đều gửi đến activity

![image](https://user-images.githubusercontent.com/65121835/185863169-57637d94-a1b3-4421-a64e-54b2635f2431.png)

- Nó cũng nhận lại các action từ activity gửi qua

![image](https://user-images.githubusercontent.com/65121835/185863510-58daef73-12ba-47b2-9c6f-55b21b78680b.png)


2.Trong MyReceiver
- Nó nhận action và intent từ MyService

![image](https://user-images.githubusercontent.com/65121835/185863698-67e667d1-15a7-4aba-a7f8-e4a697db4b16.png)

3.Trong MainActivity
- Lắng nghe sự thay đổi từ broadcast

![image](https://user-images.githubusercontent.com/65121835/185863930-577bf349-0995-4d24-8c40-4516901cca09.png)


- Đối tượng broadcast receiver nhận dữ liệu từ broadcast

![image](https://user-images.githubusercontent.com/65121835/185864039-91527474-081e-40ef-b001-012a4457f063.png)


+ Xử lý layout với dữ liệu nhận từ broadcast

![image](https://user-images.githubusercontent.com/65121835/185864222-d32db781-7beb-4016-9979-0ac0fd6ec15d.png)

+ Hiển thị bài nhạc và gửi action trở lại service

![image](https://user-images.githubusercontent.com/65121835/185864325-ced24a56-06d1-4254-ab02-e0eec0c38711.png)

+ Nút pause và resume

![image](https://user-images.githubusercontent.com/65121835/185864379-07f99664-2231-4196-8fc4-f3928968a9aa.png)

+ hàm gửi lên service

![image](https://user-images.githubusercontent.com/65121835/185864474-db36fcf8-551d-4372-bc66-3e49aecc8ad9.png)
