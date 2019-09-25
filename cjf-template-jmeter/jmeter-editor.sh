export HEAP="-Xms1g -Xmx2g -XX:MaxMetaspaceSize=256m"
jmeter -Jproject.home=$HOME/git/cjf-template/cjf-template-jmeter -Jincludecontroller.prefix=$HOME/git/cjf-template/cjf-template-jmeter/src/main/test/resources/fragments/
