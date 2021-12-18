# java-webcrawler

Usage

mvn package
java -jar {path_to_fatjar} {config.json path}

eg java -jar java-webcrawler-1.0-SNAPSHOT-jar-with-dependencies.jar config.json

```yaml
config.json
{
  "NUM_SPIDERS": 10,  // total number of parallel threads
  "MAX_LEVEL": 3, // bfs depth
  "REGEX_TO_EXTRACT": "(\\+\\d{1,2}\\s?)?1?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}",  // to extract mobile numbers, user can specifiy their own regex
  "INITIAL_QUERIES_FILE" : "./queries.txt",  // initial search queries to be written in this file newline seperated
  "SEARCH_ENGINES": "google" // where you want to search your query initially, only google supported as of now
}
```
