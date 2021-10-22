# PinkPony
Pink pony is a free software command-line application that suggest functional clusters based on the common changes on git.  
> <i> What changes together should be also together </i>

## Result
The application produces 2 files.    
* `cluster.txt`: contains the clusters;
* `graphViz.dot`: a [.dot](https://en.wikipedia.org/wiki/DOT_(graph_description_language)) file to visualize the co-change graph
## Usage
To run the Pink pony application you have to download the [recently released version](https://github.com/Pavlmits/PinkPony/releases/latest).

#### Clustering algorithms options
* `mr`: [Markov Clustering] is a hard clustering algorithm;
* `ch`: [Chinese Whispers] is a hard clustering algorithm;
* `max`: [MaxMax] is a soft clustering algorithm for undirected graphs;
* `watset`: [Watset] is a *local-global meta-algorithm* for fuzzy graph clustering.

The implementation of the algorithms used from [Watset project](https://github.com/nlpub/watset-java)

#### Level of clustering
Depending on the investigation that you want to do, the application provides two options:

* `file` : Suggest functional clusters on file level. 
* `module` : Suggest functional clusters on module level.

* This will suggest functional clusters from files.
```bash
$ java -jar pinkpony.jar path\to\.git file max
```
* This will suggest modules under the spesific module
##### Example
###### input
<pre>
moduleName
    file1
    file2
    ...
    fileN
</pre>

###### output 
<pre>
    cluster1
        file1
        file3
    cluster2
        file6
        file89
        ...
    clusterN
        file2
        file15
</pre>
```bash
$ java -jar pinkpony.jar path\to\.git file max moduleName
```

* The following command will suggest new co-change modules from the submodules of mod1, mod2, mod3 with the [Markov Clustering] algorithm.
##### Exaple
###### input 
<pre>
mod1
    subMod1 
    sudMod2 
    subMod3 
mod2 
    subMod4 
    sudMod5 
mod3 
    subMod6 
    sudMod7 
    subMod8 
    subMod9 
 </pre>
###### output
<pre> 
cluster1        
    subMod3
    subMod9
    subMod8
cluster2
    subMod4
    subMod5
    subMod1
...
</pre>
    
```bash
$ java -jar pinkpony.jar path\to\.git module mr mod1 mod1 mod2  
```


The Pink Pony application is a part of my thesis project.

[Markov Clustering]: https://doi.org/10.1137/040608635
[Chinese Whispers]: https://dl.acm.org/citation.cfm?id=1654774
[MaxMax]: https://doi.org/10.1007/978-3-642-37247-6_30
[Watset]: https://doi.org/10.1162/COLI_a_00354
