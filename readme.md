docker-compose up
java -jar spring-cloud-dataflow-shell-2.10.1.jar
dataflow config server --uri http://localhost:9393
app register --name my-task --type task --uri file:///tasks/jars/poc-task-1.0-SNAPSHOT.jar
task create my-task-instance --definition "my-task"
