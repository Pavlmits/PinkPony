# PinkPony
Pink pony is a free software command-line application that suggest functional clusters based on the common changes on git.  
> <i> What changes together should be also together </i>

## Usage
To run the Pink pony application you have to download the [recently released version](https://github.com/Pavlmits/PinkPony/releases/latest).

#### Clustering algorithms options
* `mr`: [Markov Clustering] is hard clustering algorithm;
* `ch`: [Chinese Whispers] is a hard clustering algorithm;
* `max`: [MaxMax] is a soft clustering algorithm for undirected graphs;
* `watset`: [Watset] is a *local-global meta-algorithm* for fuzzy graph clustering.

The implementation of the algorithms used from [Watset](https://github.com/nlpub/watset-java)

#### Level of clustering
Depends the investigation that you want to do, the application provides two kind of options.

* `file` : Suggect functional clusters on file level. 
* `module` : Suggect functional clusters on module level.

```bash
java -jar pinkpony.jar path\to\.git file max
```

[Markov Clustering]: https://doi.org/10.1137/040608635
[Chinese Whispers]: https://dl.acm.org/citation.cfm?id=1654774
[MaxMax]: https://doi.org/10.1007/978-3-642-37247-6_30
[Watset]: https://doi.org/10.1162/COLI_a_00354
