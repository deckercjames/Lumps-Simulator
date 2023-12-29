

# compile
mkdir -p bin/
javac -d bin/ src/*.java

# run
java -cp bin/ Lumps "$@"
