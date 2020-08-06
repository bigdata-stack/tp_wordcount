# TP : Hadoop Map/Reduce "Word Count"


Les différentes manipulations de ce support (et ceux à venir) se feront via le
NameNode et quel que soit la taille du cluster.

# 1. Exemple de WordCount.java
1.1. Connexion au cluster Hadoop
  (1) Se connecter sur le NameNode avec MobaXterm en créant une session de connexion SSH avec l'utilisateur hadoop.
  
1.2. Chargement des fichiers dans le système HDFS (« HaDoop File System »)
  (2) Créer un répertoire dans HDFS via la commande suivante sur le prompt. 
  
    hadoop dfs –mkdir rficTPWC
    
(3) Vérifier la présence du répertoire dans HDFS.

  hadoop dfs –ls
  
(4) Placer les fichiers fournis fichier1.txt et fichier2.txt dans le répertoire que vous venez de créer sur HDFS. Attention bien faire la distinction entre le
contenu de votre machine, de la machine virtuelle NameNode et du HDFS.

  hadoop dfs –put fichier*.txt rficTPWC
  
(5) Vérifier leur présence dans le répertoire via la commande ci-dessous ou l'interface web (http://192.168.56.50:50070).

  hadoop dfs –ls rficTPWC/
  
1.3. Mise en place du traitement Map/Reduce Cet exemple repose sur le schéma d’entrées/sorties du processus Map/Reduce suivant : 

    <Hello World,Bye World!>           MAP1 {(Hello,1), (World,1), (Bye,1), (World,1)}
    <Hello hadoop,Goodbye to hadoop.>  MAP2 {(Hello,1),(hadoop,1),(Goodbye,1),(to,1),(hadoop,1)}

    REDUCE {(Hello,2),(World,2),(Bye,1),(hadoop,2),(Goodbye,1),(to,1)}

(6) Suivre les directives fournies dans le support intitulé "Hadoop & Eclipse" pour créer le jar correspondant aux 3 fichiers java fournis (WordCount.java,
WordCountMapper.java, WordCountReducer.java)

(7) Lancer l’application Map/Reduce dans le Cluster Hadoop (on suppose que le jar créé se nomme wordcount.jar).
    
    hadoop jar wordcount.jar WordCount rficTPWC rfic-output

** Remarque ** : les 2 répertoires (entrée/sortie) sont utilisés dans le fichier WordCount.java (lignes 29 et 30). Notez que le répertoire de sortie avant l'exécution
est vidé de son contenu (lignes 32 & 33).

(8) Dans la trace d’exécution, comprendre et expliquer les informations encadrées sur le déroulement de l’exécution (vue partielle de la trace) :

  INFO mapred.JobClient: Total input paths to process : 2
  INFO mapred.JobClient: Loaded the native-hadoop library
  INFO mapred.JobClient: Snappy native library not loaded
  INFO mapred.JobClient: Running job: job_201607141442_0004
  INFO mapred.JobClient: map 0% reduce 0%
  INFO mapred.JobClient: map 100% reduce 0%
  INFO mapred.JobClient: map 100% reduce 33%
  INFO mapred.JobClient: map 100% reduce 100%
  INFO mapred.JobClient: Job complete: job_201607141442_0004
  INFO mapred.JobClient: Launched reduce tasks=1
  INFO mapred.JobClient: Launched map tasks=2
  INFO mapred.JobClient: Data-local map tasks=1
  INFO mapred.JobClient: Bytes Written=46
  INFO mapred.JobClient: FileSystemCounters
  INFO mapred.JobClient: FILE_BYTES_READ=113
  INFO mapred.JobClient: Map input records=2
  INFO mapred.JobClient: Reduce shuffle bytes=119
  INFO mapred.JobClient: Spilled Records=18
  INFO mapred.JobClient: Map output bytes=89
  INFO mapred.JobClient: CPU time spent (ms)=730
  INFO mapred.JobClient: Combine input records=0
  INFO mapred.JobClient: Reduce input records=9
  INFO mapred.JobClient: Reduce input groups=6
  INFO mapred.JobClient: Combine output records=0
  INFO mapred.JobClient: Reduce output records=6
  INFO mapred.JobClient: Map output records=9
  
(9) Vérifier l’existence du fichier résultat

  hadoop dfs -ls rfic-output
 >>> /user/hadoop/rfic-output/part-r-00000
 
(10) Visualiser le contenu du fichier résultat

  hadoop dfs -cat rfic-output/part-r-00000

  Bye 1
  Goodbye 1
  Hello 2
  World 2
  hadoop 2
  to 1
  
(11) Tester le fonctionnement de ce programme avec le fichier ulyss.txt.

2. Optimisation de WordCount.java . Reprendre le code source de l'exemple WordCount en considérant le nouveau schéma des entrées/sorties du processus Map/Reduce qui vise à limiter le nombre de couples (clé,valeur) échangés entre Map et Reduce.

    <Hello World, Bye World!>            MAP1 {(Hello,1), (World,2), (Bye,1)} 
    <Hello hadoop, Goodbye to hadoop.>   MAP2 {(Hello,1),(hadoop,2),(Goodbye,1),(to,1)}  
    REDUCE  {(Hello,2), (World,2), (Bye,1), (hadoop,2), (Goodbye,1),(to,1)}

(12) Implanter le schéma proposé en Java.
(13) Montrer l’optimisation obtenue par l’implantation du nouveau schéma. Pour cela utiliser la trace d’exécution Hadoop commentée à l’étape (1.3.7).
Remarques : les instances de Mapper et Reducer sont créées au début del'exécution. L'appel à la méthode map quant à elle est exécutée pour chaque portion de données à traiter. Cette méthode peut donc être appelée plusieurs fois pour la même instance de la classe Mapper (idem pour Reducer).

3. Vers un nouveau traitement Sur la base des traitements ci-dessus, nous souhaitons désormais obtenir le nombre de mots trouvés par première lettre (après la conversion de la casse en minuscule)
c'est-à-dire :

  b 1
  g 1
  h 4
  t 1
  w 2
  
(1) Pour obtenir ce résultat, peut-on simplement modifier le comportement du Reducer ?
(2) Proposer un traitement Map/Reduce adapté (algorithme Map et algorithme du Reduce).
(3) Implanter en java ce nouveau traitement sur la base des fichiers java des questions précédentes
