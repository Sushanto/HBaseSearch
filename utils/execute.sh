HBASE_LIB1="/home/hduser/hbase/lib/*"
HBASE_LIB2="/usr/local/hadoop/share/hadoop/common/lib/*"
if [ $# -lt 1 ]
then
	echo "Argument required"
	exit
fi


# echo "Start compiling..." $filename
# javac -Xlint -cp ".:$HBASE_LIB1:$HBASE_LIB2" $filename
# echo "Compiled, Running........." $classname

java -cp ".:$HBASE_LIB1:$HBASE_LIB2" $1