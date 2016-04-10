package codegen

import scala.util.parsing.combinator.RegexParsers
import java.io.{File, FileWriter, PrintWriter}
import org.apache.commons.io.FileUtils

object FakeCompiler {
  // This code will fail to compile with scala-2.10 (see https://issues.scala-lang.org/browse/SI-6189)
  case class ManyArgs(
    a1 : Int, a2: Int, a3 : Int, a4: Int, a5 : Int, a6: Int, a7 : Int, a8: Int, a9 : Int, a10: Int,
    a11 : Int, a12: Int, a13 : Int, a14: Int, a15 : Int, a16: Int, a17 : Int, a18: Int, a19 : Int, a20: Int,
    a21 : Int, a22: Int, a23 : Int, a24: Int, a25 : Int, a26: Int, a27 : Int, a28: Int, a29 : Int, a30: Int
                     )

  def main(args: Array[String]) = {
    println(ManyArgs(1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10))
    println("Running the fake compiler with argument " + args(0))

    object TestParser extends RegexParsers {
    }

    val outputFile = new File(args(0))
    FileUtils.forceMkdir(outputFile.getParentFile);
    val fileWriter = new PrintWriter(new FileWriter(outputFile))
    fileWriter.println("package generated")
    fileWriter.println("object Output { def main(args: Array[String]) = { println(\"Yay\") }}")

    println("Closing file " + args(0))
    fileWriter.close()
  }
}
