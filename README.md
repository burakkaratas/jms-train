## jms-train

# Create Messaging Broker on Artemis

$ ./artemis create ~/projects/tools/brokers/train-broker &admin &admin &y

$ /projects/tools/brokers/train-broker/bin/artemis run

configuration file : /etc/broker.xml

broker.xml
      <security-settings>
         <security-setting match="karatas.queues.request.#">
            <permission type="createNonDurableQueue" roles="role1,role2"/>
            <permission type="deleteNonDurableQueue" roles="role1,role2"/>
            <permission type="createDurableQueue" roles="role1,role2"/>
            <permission type="deleteDurableQueue" roles="role1,role2"/>
            <permission type="createAddress" roles="role1"/>
            <permission type="deleteAddress" roles="role1"/>
            <permission type="consume" roles="role2"/>
            <permission type="browse" roles="role1"/>
            <permission type="send" roles="role1"/>
            <!-- we need this otherwise ./artemis data imp wouldn't work -->
            <permission type="manage" roles="amq"/>
        </security-setting>
        <security-setting match="karatas.queues.reply.#">
            <permission type="createNonDurableQueue" roles="role1"/>
            <permission type="deleteNonDurableQueue" roles="role1"/>
            <permission type="createDurableQueue" roles="role1"/>
            <permission type="deleteDurableQueue" roles="role1"/>
            <permission type="createAddress" roles="role1"/>
            <permission type="deleteAddress" roles="role1"/>
            <permission type="consume" roles="role2,role1"/>
            <permission type="browse" roles="role1"/>
            <permission type="send" roles="role1"/>
            <!-- we need this otherwise ./artemis data imp wouldn't work -->
            <permission type="manage" roles="amq"/>
        </security-setting>
....

artemis-roles.properties
role1 = user1
role2 = user2

artemis-users.properties
username | password
user1 = user1
user2 = user2
