*Steps for installing ElasticSearch in your machine:*

Download ElasticSearch 8.6.0 from https://www.elastic.co/guide/en/elasticsearch/reference/current/zip-windows.html

(If you are having experience in Docker, you can use this docker method: https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html)

Extract the elasticsearch-8.6.0.zip file to C:\Program Files  (or any other program folder)

Add following 5 lines in config/elasticsearch.yml:

action.auto_create_index: .monitoring*,.watches,.triggered_watches,.watcher-history*,.ml*
ingest.geoip.downloader.enabled: false
xpack.security.enabled: false
xpack.security.transport.ssl.enabled: false
xpack.security.http.ssl.enabled: false


Add following 2 lines in config/jvm.options file
-Xms2g
-Xmx2g


Open Windows command prompt
cd "C:\Program Files\elasticsearch-8.6.0"
Install ElasticSearch using following comnmand
C:\Program Files\elasticsearch-8.6.0>.\bin\elasticsearch.bat

Wait for several minutes, You will see some details regarding Username, Password, Access Token.  Copy those credentials and keep in a safe place.
To confirm whether it started or not, open the browser and enter https://localhost:9200/   When it asks for username and password, enter the account details which you copied earlier.
Now, In the browser you will see an output like below
{
  "name" : "ROLO-HOME-LAB",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "rIrrUgewQOeKEM3uz-9kDg",
  "version" : {
    "number" : "8.6.0",
    "build_flavor" : "default",
    "build_type" : "zip",
    "build_hash" : "f67ef2df40237445caa70e2fef79471cc608d70d",
    "build_date" : "2023-01-04T09:35:21.782467981Z",
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

(For Development purpose, You have to add SSL certificate of ElasticSearch into Java Security certificates. To this, install the JDK 19, and then run the following command (put the right file path).

"C:\Program Files\Java\jdk-19\bin\keytool" -import -alias es_certificate -keystore  "C:\\Program Files\\Java\\jdk-19\\lib\\security\\cacerts" -file "C:\\NewProgramFiles\\elasticsearch-8.6.0\\config\\certs\\http_ca.crt"

(Note: For Development and Debugging purposes, You can install an Chrome extension called "Multi ElasticSeach Head")
