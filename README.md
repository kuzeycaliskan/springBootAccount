Docker Execution

docker build . -t account:1.0

docker run --name account -d -p 9090:8080 account:1.0

post requesti suanda calistiramiyorum cunku account id bilmiyorum.
bunu cozmenin iki yolu var. ya console log acarak docker'i ayaga kaldirmak
ya da account id'leri get eden bir endpoint yazmak