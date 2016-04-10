lazy val MyCodeGeneratorConfig = config("myCodeGenerator") hide
lazy val customGeneratorTask = taskKey[Seq[File]]("Custom code generator")

lazy val root = (project in file("."))
  .configs(MyCodeGeneratorConfig)
  .settings(inConfig(MyCodeGeneratorConfig)(Defaults.compileSettings) :_*)
  .settings(
    scalaVersion := "2.11.8",
    ivyConfigurations += MyCodeGeneratorConfig,

    // This is a Scala 2.11 library to be used from the code generator (even when using sbt built against
    // Scala 2.10)
    libraryDependencies += "org.scala-lang" % "scala-library" % scalaVersion.value % "myCodeGenerator",
    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4" % "myCodeGenerator",
    libraryDependencies += "commons-io" % "commons-io"%	"2.4" % "myCodeGenerator",

    fork in MyCodeGeneratorConfig := true,
    customGeneratorTask in MyCodeGeneratorConfig := {
      (compile in MyCodeGeneratorConfig).value

      val target = (managedSourceDirectories in Compile).value(0) / "generated" / "Output.scala"

      toError(runner.value.run("codegen.FakeCompiler",
        (fullClasspath in MyCodeGeneratorConfig).value.map(_.data),
        Seq(target.absolutePath), streams.value.log))

      Seq[File](target)
    },

    sourceGenerators in Compile += (customGeneratorTask in MyCodeGeneratorConfig).taskValue
  )
