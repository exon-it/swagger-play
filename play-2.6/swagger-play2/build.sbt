lazy val repo: Option[String] = sys.props.get("publishTo")

organization := "by.exonit"
name := "swagger-play2-play26"
version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.12"
crossScalaVersions := Seq(scalaVersion.value, "2.12.4")

resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies ++= Seq(
  "com.fasterxml.jackson.module"  %% "jackson-module-scala" % "2.9.2",
  "org.slf4j"          % "slf4j-api"                  % "1.7.25",
  "io.swagger"         % "swagger-core"               % "1.5.17",
  "io.swagger"        %% "swagger-scala-module"       % "1.0.4",
  "com.typesafe.play" %% "routes-compiler"            % "2.6.10",
  "com.typesafe.play" %% "play-ebean"                 % "4.1.0"            % "test",
  "org.specs2"        %% "specs2-core"                % "4.0.2"            % "test",
  "org.specs2"        %% "specs2-mock"                % "4.0.2"            % "test",
  "org.specs2"        %% "specs2-junit"               % "4.0.2"            % "test",
  "org.mockito"        % "mockito-core"               % "2.13.0"            % "test")

mappings in (Compile, packageBin) ~= { _.filter(!_._1.getName.equals("logback.xml")) }

publishArtifact in Test := false
publishMavenStyle := true
bintrayOrganization := Some("exon-it")
bintrayRepository := "maven-releases"
bintrayReleaseOnPublish in ThisBuild := false

pomIncludeRepository := { _ => false }
licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.txt"))
homepage := Some(url("https://github.com/exon-it/swagger-play2"))
scmInfo := Some(ScmInfo(
  url("https://github.com/exon-it/swagger-play2"),
  "scm:git:https://github.com/exon-it/swagger-play2.git",
  Some("scm:git:git@github.com:exon-it/swagger-play2.git")))
pomExtra := {
  <developers>
    <developer>
      <id>fehguy</id>
      <name>Tony Tam</name>
      <email>fehguy@gmail.com</email>
    </developer>
    <developer>
      <id>ayush</id>
      <name>Ayush Gupta</name>
      <email>ayush@glugbot.com</email>
    </developer>
    <developer>
      <id>rayyildiz</id>
      <name>Ramazan AYYILDIZ</name>
      <email>rayyildiz@gmail.com</email>
    </developer>
    <developer>
      <id>benmccann</id>
      <name>Ben McCann</name>
      <url>http://www.benmccann.com/</url>
    </developer>
    <developer>
      <id>frantuma</id>
      <name>Francesco Tumanischvili</name>
      <url>http://www.ft-software.net/</url>
    </developer>
    <developer>
      <id>antonov_i</id>
      <name>Igor Antonov</name>
      <email>antonov_i@exon-it.by</email>
      <organization>Exon IT</organization>
      <organizationUrl>http://exonit.by</organizationUrl>
      <timezone>Europe/Minsk</timezone>
    </developer>
  </developers>
}
credentials ++= sys.props.get("credentialPath").map {cp =>
  cp.split(',').map {path => Credentials(file(path))}.toSeq
} getOrElse Seq.empty

lazy val root = (project in file(".")).enablePlugins(PlayScala)
