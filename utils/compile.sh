HBASE_LIB1="/home/hduser/hbase/lib/*"
HBASE_LIB2="/usr/local/hadoop/share/hadoop/common/lib/*"
if [ $# -lt 1 ]
then
	echo "Argument required"
	exit
fi

for i in $@; do
	echo "Start compiling..." $i
	javac -Xlint -cp " .:$HBASE_LIB1:$HBASE_LIB2 " $i
	# echo "Compiled, Running........." $classname
done
# java -cp ".:$HBASE_LIB1:$HBASE_LIB2" $classname
