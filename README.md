Giao tiếp giữa Activity và ForegroundService thông qua broadcast receiver

1.Thực hiện custom một view cho notification
- Có nút pause, resume và clear nhạc trên notification

![image](https://user-images.githubusercontent.com/65121835/185851391-260934ac-63c1-4a1b-93a1-a8e9b498c2f3.png)

- Trong Myservice ta thực hiện đưa layout đó lên giao diện

![image](https://user-images.githubusercontent.com/65121835/185851531-db072513-e7e5-4c89-b5de-21ac680a49d8.png)

2.Giao tiếp với notification nhạc 

- Ta sẽ thực hiện lấy action của notifcaiton để xử lý 

![image](https://user-images.githubusercontent.com/65121835/185851785-b8b7686a-933a-4b80-9c11-ccd99c03ddd6.png)

- Lắng nghe action truyền tới broadcast receiver

![image](https://user-images.githubusercontent.com/65121835/185851904-f5a878aa-4c77-4ccb-9861-b965d3b4fe15.png)

- Xử lý sự thay đổi trong onStartCommand()

![image](https://user-images.githubusercontent.com/65121835/185852008-7ddc9f8c-a423-4b17-b325-6716cfbd50cc.png)


- Thực hiện hành động với action truyền vào từ onStartCommand()

![image](https://user-images.githubusercontent.com/65121835/185852078-16ca11ca-f9c9-42e3-b09e-9de1e83a6acd.png)

- Chú ý: sử dụng remoteViews.setOnClickPendingIntent để bắt sự kiện trên notification
+ Sau đó đem nó xử lý qua broadcast receiver và gửi trở lại onStartCommand()

![image](https://user-images.githubusercontent.com/65121835/185852237-ad7f71fe-600c-4386-96b3-4d9354525b8a.png)

- Chú ý : đăng ký broadcast receiver

![image](https://user-images.githubusercontent.com/65121835/185852424-d6031cad-137e-4fda-9b95-f21f4211b28b.png)


3.Chú ý
- Với notification, vì nó cùng channel id nên nó sẽ tiếp tục cái nhạc đang phát thay vì nó tạo mới.
- Khi gọi tới mediaPlayer.start(); nó sẽ tiếp tục notification
