import sys

if(len(sys.argv) != 2):
    print ("Usage:\npython3", sys.argv[0], "<filename>\nor\npython3",  sys.argv[0] , "-h")
    exit(1)

if(sys.argv[1] == "-h"):
    print("""Thanks for trying out the assembler. Please report all errors to samuelgassman7@gwu.edu. While I will try my best to fix any errors, note that this code comes with no warranty neither express nor implied of correctness, not even of merchantability. Usage of this assembler means you agree that you will not hold the author liable for any issues caused directly or indirectly from the use of this code.

    (In less legal terms: "I don't know if this thing works perfectly. I wrote it as a quick helper script. If you want to use it, cool. If not, also cool. But, I can't make any promise that it works the way you'll want").

    To use this assembler you will need the following:
    - the file 'instructions.log' which is my configuration file containing the specs for each instruction
    - an 'asm' (assembly) file of your own that you want to assemble down to hex. you will pass that filename as the 1st CMDLINE arg.

Here is some example syntax for your asm file (do me a favor and don't play around with whitespace too much. No clue if that will break things):
<addr>: <instruction>

6:  LDA 1, 0, 1
7:  LDA 1, 0, 1, i
8:  SRC 0, 1, 1, 4
9:  STR 0, 0, 10
10: HLT

NOTE: the assembler is not going to do much to check the validity of your code. It will not check to see whether you use an address that is restricted (below 6), nor will it perform any other checks really. All it is meant to do is convert your instruction to hex.

The assembler will then create a new file <yourInputFile>.hex with the converted hex code.""")
    exit(0)

confLines = None
try:
    with open("instructions.log") as conf:
        confLines = conf.read()
except:
    print("File 'instructions.log' (the ISA config file) not found. Please make sure it is in this same directory")
    exit(1)
    
conf = confLines
conf = conf.replace("{","")
conf = conf.split("}")

instMap = dict()
for blob in conf:
    miniMap = dict()
    instArr = blob.split("\n")
    ##remove blank line

    while(len(instArr) > 0 and instArr[0].strip() == ""):
        instArr.pop(0)

    if(len(instArr) == 0):
        continue

    ##get the contents after the equal sign for each of
    ##the following lines:
    ##op = N
    ##name = <str>
    ##grps = X
    op = instArr[0][instArr[0].find("=")+1:]
    name = instArr[1][instArr[1].find("=")+1:]
    groups = instArr[2][instArr[2].find("=")+1:]
    miniMap['op'] = int(op)
    miniMap['name'] = name
    miniMap['groups'] = int(groups)

    for x in range(1,int(groups)+1):
        i = str(x)

        miniMap['idx'+i] = instArr[2+x][instArr[2+x].find("=")+1:]

        miniMap['type'+i] = instArr[2+x+int(groups)][instArr[2+x+int(groups)].find("=")+1:]


    instMap[name] = miniMap


lines = None
hexLines = []
try:
    with open(sys.argv[1]) as file:
        pass
except:
    print("Your file: '" + sys.argv[1] + "' was not found.")
    exit(1)
with open(sys.argv[1]) as file:
    lines = file.readlines()
    for x in lines:

        if(len(x.strip()) == 0):
            continue
        ###for a line like:
        ###0: LDA, 0, 0, 31[, i]
        ###remove the address section first:
        addr = x.strip()[0:x.strip().find(":")]
        x = x.strip()[x.strip().find(":")+1:].strip()
        ###extract just the instruction
        opcode = x[:]
        if(x.find(' ') != -1):
            opcode = x[:x.find(' ')]
        x = x[x.find(' ')+1:].strip()
        line = x.split(",")


        ###get the instruction from the map
        inst = instMap[opcode.upper().strip()]

        binOp = '{0:06b}'.format(inst['op'])
        instruction = binOp
        hasIndirect = (opcode.upper().strip() in ['LDR', 'STR', 'LDA', 'LDX', 'STX', 'JZ', 'JNE', 'JCC', 'JMA', 'JSR', 'SOB', 'JGE', 'AMR', 'SMR'])
        isIndirect = False
        for x in line:
            if x.strip() == 'i':
                isIndirect = True


        lineIdx = 0

        for y in range(2, inst['groups']):
            if(y == inst['groups']-1):
                opLen = str(16-len(instruction))
            else:
                opLen = str(int(inst['idx'+str(y+1)])-int(inst['idx'+str(y)]))
            if(inst['type'+str(y)] != "BLANK"):
                instruction += ('{0:0' +opLen + 'b}').format(int(line[lineIdx].strip()))
                lineIdx+=1
            else:
                instruction += '0'*int(opLen)

        if(hasIndirect and isIndirect): 
            instruction = instruction[:10]+'1' +instruction[11:]

        hexInst = '{0:04x}'.format(int(instruction, 2))
        hexAddr = '{0:04x}'.format(int(addr))
        
        hexLines.append(hexAddr + " " + hexInst)


with open(sys.argv[1] + ".hex", "w") as out:
    for x in (hexLines):
        out.write(x + "\n")

print("Wrote to file: ", sys.argv[1] + ".hex")



