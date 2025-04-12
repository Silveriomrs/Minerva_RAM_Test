<!DOCTYPE HTML>
<html>
<head>
<title>Faulty Ram Decoder v0.3</title>
<h2 style='color: red;'>Minerva (Sinclair QL) Faulty Ram Decoder v0.3</h2>
<hr width="50%" color="red" align="left">

<!-- Give some style to the table & inputs in order to visualize it better -->
<style>
table {
  border-collapse: collapse;
  width: 100%;
}

tr:nth-child(odd) {background-color: #D6EEEE;}

input[type=text] {text-transform: uppercase;}

input[type=text]:focus {
  background-color: lightblue;
  text-transform: uppercase;
}
</style>
</head>

<body>

<h3>Describe your system</h3>
<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">

<!-- Fields for system description -->
Do you have an internal RAM expansion that expand it to?
<br><input type="radio" id="128K" name="typeramexp" value="128" checked><label for="128K">None (128K)</label>
<input type="radio" id="512K" name="typeramexp" value="512"><label for="512K">512K</label>
<input type="radio" id="640K" name="typeramexp" value="640"><label for="640K">640K</label>
<h3>Introduce screen values</h3>

<!-- Fields for datas obtenined in a table format-->
WRITE DATA: <input type="text" name="write" placeholder="548C4878" maxlength="8" size="8"><br><br>
READ DATA: &nbsp;&nbsp;<input type="text" name="read" placeholder="5CCD5CCD" maxlength="8" size="8"><br><br>
ADDRESS: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="address" placeholder="00032000" maxlength="8" size="8"><br><br>
<input type="submit" name="submit" value="Submit">
<input type='reset' value='Reset' name='reset'>
<br>
<i><small> * Submiting without all data entered, it displays an example. </small></i>
<br><br>
</form>
<!-- <hr width="95%" color="green">

<!-- Result or Example area -->

<hr width="35%" color="green" align="left">


<?php
// Xad/Nightfall (8/5/2015)
// Modified by Popopo (09/04/2025)

// Write below the output of the Minerva ROM Memory check:
// Faulty Ram Decoder v0.3 Online: http://www.nightfallcrew.com/minervaram

//Flag to determinate state of example or real calc
$example = empty($_POST['write']) || empty($_POST['read']) || empty($_POST['address']);

$typeramexp = isset($_POST['typeramexp'])? $_POST['typeramexp'] : "128";
$write = hexdec(!empty($_POST['write']) ? $_POST['write'] : "548C4878");
$read  = hexdec(!empty($_POST['read'])? $_POST['read'] : "5CCD5CCD");
$address = hexdec(!empty($_POST['address'])? $_POST['address'] : "00032000");
$results = 0;
$ix = 0;
//Top of the RAM in the equipment, by default set to 128K + Reserved area (ROM,IO,etc)
$M_TOP = hexdec("40000");
//For non stock QL set the top address of RAM
if ($typeramexp == "512") {
    $M_TOP = hexdec("A0000");
} elseif ($typeramexp == "640"){
    $M_TOP = hexdec("C0000");
}

//Floor of the RAM in tue equipment over the ROM and reserved memory.
$M_BASE = hexdec("20000");
//Calc the middle point address of the RAM in the equipment.
$M_MIDDLE = (($M_TOP - $M_BASE)/2) + $M_BASE;

// -----------------------------------------

if($example) {echo "<h3 style='color: purple;'".">Example</h3>";}
else {echo "<h3 style='color: blue;'".">Result</h3>";}

echo "- MINERVA OUTPUT SCREEN -<br>";
echo "<hr width='20%'"." color='margenta'"." align='left'>";

echo "Internal RAM : ".$typeramexp."K<BR><BR>";
echo '<table style="width:12%">';
echo "<TR><TD>WRITE</TD><TD>".strtoupper(dechex($write))."</TD></TR>";
echo "<TR><TD>READ</TD><TD>".strtoupper(dechex($read))."</TD></TR>";
echo "<TR><TD>ADDRESS</TD><TD>".strtoupper(dechex($address))."</TD></TR>";
echo "</table>";
echo "<hr width='20%'"." color='margenta'"." align='left'>";

echo "<br>";
//Check if it is upper the top of the present internal RAM
if ($address >= $M_TOP){
	echo "Faulty ram is in expansion memory<br>";
	exit;
}

//Calc every 2 hex bytes from Write vs Read
for ($i = 0; $i <= 6; $i += 2)
{
    $results = $results|hexdec(substr(dechex($write), $i, 2))^hexdec(substr(dechex($read), $i, 2));
}

//Converting to binary the hex results
$resultsToBinary = substr("00000000",0,8 - strlen(decbin($results))).decbin($results);
//Shows the Address and conversion to binary of the result.
echo "ADDRESS > ".strtoupper(dechex($M_MIDDLE))."<br>";
echo "BINARY STRING: $resultsToBinary<br><br>";
    
//Midle of the RAM to the top
if ($address >= $M_MIDDLE){
    $ram = array("IC16","IC15","IC14","IC13","IC12","IC11","IC10","IC9");
    foreach($ram as $value)
    {
        if (substr($resultsToBinary, $ix, 1) == 1)
        {
            echo $value.' (<font color="red">BAD</font>)<br>';
        } else {
            echo $value.' (<font color="green">GOOD</font>)<br>';
        }
    $ix++;
    }
    echo "<br>";
//From the floor of the RAM to the middle.
} else {
    $ram = array("IC8","IC7","IC6","IC5","IC4","IC3","IC2","IC1");
    foreach($ram as $value)
    {
        if (substr($resultsToBinary, $ix, 1) == 1)
        {
            echo $value.' (<font color="red">BAD</font>)<br>';
        } else {
            echo $value.' (<font color="green">GOOD</font>)<br>';
        }
    $ix++;
    }
    echo "<br>";
}


/* Output Screen:

Minerva (Sinclair QL) Ram Fail Decoder v0.2

- MINERVA OUTPUT SCREEN -
-------------------------
WRITE:    5 4 8 C 4 8 7 8
READ:     5 C C D 5 C C D
ADDRESS:  0 0 0 3 2 0 0 0
-------------------------

ADDRESS > 30000
BINARY STRING: 11111101

IC16 (BAD)
IC15 (BAD)
IC14 (BAD)
IC13 (BAD)
IC12 (BAD)
IC11 (BAD)
IC10 (GOOD)
IC9 (BAD)

The Original Program (ram3_ramfail.bas) was written for the SINCLAIR QL in SUPER BASIC language by Dominic Brown (1990).
Converted in PHP language by Xad/Nightfall (8/5/2015).

*/
?>

<?php echo $info; ?>

</body>

<!-- Zone of credits -->
<footer>
<hr width="95%" color="green" align="left">
<h3>Credits</h3>
The Original Program (ram3_ramfail.bas) was written for the SINCLAIR QL in SUPER BASIC language by Dominic Brown (1990).
<i><br>Converted in PHP language by Xad/Nightfall (8/5/2015).
<br>Modified by Popopo (09/04/2025).</i>
</footer>
</html>
