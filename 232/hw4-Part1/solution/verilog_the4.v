`timescale 1ns / 1ps


module add_checksum(
	input [1:9] acInput,
	output reg [1:12] acOutput
    );
	 
//Write your code below
reg [3:0] i;
reg [5:0] total;
reg [0:2] checkSum;

always @(acInput)
begin
	checkSum=0;
	total=0;
	for(i=1;i<10;i=i+1)
	begin
		total=i*acInput[i]+total;
	end
	checkSum=total%8;
	for(i=1;i<13;i=i+1)
	begin
		if(i<10)
			acOutput[i]=acInput[i];
		else
			acOutput[i]=checkSum[i-10];
	end
end

endmodule


module detect_error(
	input [1:12] decInput,
	output reg [1:9] decOutput
    );

//Write your code below

reg[3:0] i;
reg[0:2] checkSum;
reg[5:0] total;

always @(decInput)
begin
	checkSum=0;
	total=0;
	for(i=1;i<10;i=i+1)
	begin
		total=i*decInput[i]+total;
	end
	checkSum=total%8;
	if(decInput[10]==checkSum[0] && decInput[11]==checkSum[1]&& decInput[12]==checkSum[2])
	begin
		for(i=1;i<10;i=i+1)
		begin
			decOutput[i]=decInput[i];
		end
	end
	else
		decOutput=0;


end


endmodule


module RAM(
	input [8:0] rInput, 
	input CLK,
	input Mode, //0:read, 1:write
	input [3:0] rAddress,
	output reg [8:0] rOutput);

//Write your code below
reg [0:8] mem [0:15];
reg [5:0] i;

initial begin
for(i=0;i<16;i=i+1)
begin
	mem[i]=9'b111111111;
end
end

always @(posedge CLK)
begin
	if(Mode==0)
	begin
		rOutput=mem[rAddress];
	end
	else if(Mode==1)
	begin
		mem[rAddress]=rInput;
	end
end
endmodule	

module ISBN_RAM(
	input [11:0] isbnInput,
	input CLK,
	input Mode, //0:read, 1:write
	input [3:0] isbnAddress,
	output  [11:0] isbnOutput);

wire [1:9] detect_errorToRam;  
wire [1:9] ramToadd_checksum;

//DO NOT EDIT THIS MODULE
detect_error DC (isbnInput,detect_errorToRam);
RAM RM(detect_errorToRam,CLK,Mode,isbnAddress,ramToadd_checksum);
add_checksum EN (ramToadd_checksum,isbnOutput);
//DO NOT EDIT THIS MODULE
endmodule