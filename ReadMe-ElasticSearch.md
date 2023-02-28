*Steps for installing ElasticSearch in your machine:*

Download ElasticSearch 8.6.2 from https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-8.6.2-windows-x86_64.zip

(If you are having experience in Docker, you can use this docker method: https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html)

Extract the elasticsearch-8.6.2.zip file to C:\Program Files  (or any other program folder). I use cd "C:\NewProgramFiles\elasticsearch-8.6.2"

Open the ElasticSearch Configuration file config/elasticsearch.yml, and Add following 4 lines & save:

action.auto_create_index: .monitoring*,.watches,.triggered_watches,.watcher-history*,.ml*
xpack.security.enabled: false
xpack.security.transport.ssl.enabled: false
xpack.security.http.ssl.enabled: false


Open config/jvm.options file and Add following 2 lines. (Recommended value is: 50% of RAM)
-Xms2g
-Xmx2g


Note: If you are an expert, You can set cluster name and other configurations based on your project requiorement.

Open Windows command prompt
cd "C:\NewProgramFiles\elasticsearch-8.6.2"

Install ElasticSearch using following comnmand
C:\Program Files\elasticsearch-8.6.2>.\bin\elasticsearch.bat

Wait for 5 to 10 minutes.

To confirm whether it started or not, open the browser and enter http://localhost:9200/

Now, In the browser you will see an output like below
{
  "name" : "ROLO-HOME-LAB",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "tx-4rhi_R-uPNaIylGsbNw",
  "version" : {
    "number" : "8.6.2",
    "build_flavor" : "default",
    "build_type" : "zip",
    "build_hash" : "2d58d0f136141f03239816a4e360a8d17b6d8f29",
    "build_date" : "2023-02-13T09:35:20.314882762Z",
    "build_snapshot" : false,
    "lucene_version" : "9.4.2",
    "minimum_wire_compatibility_version" : "7.17.0",
    "minimum_index_compatibility_version" : "7.0.0"
  },
  "tagline" : "You Know, for Search"
}


Now, come back to command prompt then press Ctrl+C to quit this Service. Becuase we are going to add this ElasticSearch Service under Windows Services (services.msc) 

To install ES as a Service enter following command
C:\NewProgramFiles\elasticsearch-8.6.2>.\bin\elasticsearch-service install

Now Open Windows Services (services.msc) and Start the ElasticSearch Service from that service window.

Again, refresh the same browser (URL: http://localhost:9200/) and confirm if you are seeing the right output in the browser window.

Now you protect the ElasticSearch using an username and password.
To setup new password for ElasticSearch, Enter the folowing command:
C:\NewProgramFiles\elasticsearch-8.6.2>.\bin\elasticsearch-setup-passwords auto
Above command will generate a Password for the user "elastic"

If the Password was created already and if you forgot the password you can reset it using following command
C:\NewProgramFiles\elasticsearch-8.6.2>.\bin\elasticsearch-reset-password.bat -username elastic --auto


Again, refresh the same browser (URL: http://localhost:9200/), enter the username and password and confirm if you are seeing the right output in the browser window.


For Development and Debugging purposes, we have to install an Chrome extension called "Multi ElasticSeach Head". Just open Chrome Websiore in Chrome browser, search for "Multi ElasticSeach Head" and install. (This extension wont work in Incognito mode)

Now Open "Multi ElasticSeach Head" and Enter the ElasticSearch URL as http://localhost:9200/  After Entering username and password, you will see a ElasticSeach Status page.

If you see Green or Yellow status in that page. then you can confirm that the ElasticSearch installation is completed without any issue.  Keep a copy of C:\NewProgramFiles\elasticsearch-8.6.2 disrectory in a safe place.

=============================================================================================================================

References:

https://www.elastic.co/guide/en/elasticsearch/reference/8.6/zip-windows.html
(If you plan to install the latest, try https://www.elastic.co/guide/en/elasticsearch/reference/current/zip-windows.html)


Coding References:
https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html
https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-refresh.html#refresh_wait_for-force-refresh
