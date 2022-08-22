

- Sử dụng foreground service thông qua app nghe nhạc
- Nó hiển thị notification mà ta custom 
1.Trong MainActivity.java thực hiện start service 
- Truyền vào dữ liệu object song cho service

![image](https://user-images.githubusercontent.com/65121835/185835347-f67dcdc8-1ee0-4292-b3e9-b3206a3184e1.png)


2.Trong MyApplication
- Thực hiện tắt âm thanh thông báo notification
- Thực hiện khai báo notification

![image](https://user-images.githubusercontent.com/65121835/185835704-5f13afb1-a8dc-4fd9-9bb9-198e190e85c7.png)


3.Trong MyService
- Thực hiện lấy dữ liệu song
- Set custom service notification với dữ liệu song

![image](https://user-images.githubusercontent.com/65121835/185836184-de852157-6afa-427b-a5b5-80f4a0e46bcf.png)


- Sử dụng mediaPlayer để start nhạc

![image](https://user-images.githubusercontent.com/65121835/185836283-4a3b2683-7e04-4666-9d57-6c7a251bbdc9.png)

- Stop nhạc

![image](https://user-images.githubusercontent.com/65121835/185836325-e0c6ea84-3bc4-46a7-a3c6-e73c97d146fd.png)
