This program is written in Java.
There is a compilation script called "compilation_script.sh"
Before compiling, you need to add execute privilege to this compilation script

# chmod 744 compilation_script.sh

Then just type

# source compilation_script.sh

After compiling, you need to execute the program. The name of this program is "Main".

For example, when you want to get outputs of file1 you need to use the following command

# java Main ../testcase/in/file1 

Note that the output files will automatically generate in the current directory, which is
"sb6606". It's under the same directory with our Main program. 

The name of the output file is as required like "file1_FCFS", "file1_RR", and etc.

