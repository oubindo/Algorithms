## Algorithm
这个项目是Coursera Algorithm的practise assignment。我是直接上的Part 2.所以先从Part2开始，有必要会看一些Part 1的内容。

### Week1 Wordnet
题目其实就是去构建单词之间的从属关系。[说明](http://coursera.cs.princeton.edu/algs4/assignments/wordnet.html)

![](http://ovkwd4vse.bkt.clouddn.com/markdown-img-paste-20180319153459179.png)

SAP主要的算法点就是要寻找到有向图中两个节点到共有root节点的最短路径。这里使用的方法是BFS(宽度优先搜索)。通过BFS去查找到节点的上层root，并记录下root到节点的距离，这样就可以比较两个节点的多个相同root并找到最小值。

WordNet的算法点就是如何选择合理的数据结构存储节点关系。这里考虑到读取文件时要大量的增加节点，查询之前是否存在过这个节点，所以使用了HashMap。HashMap可以达到O(1)的查找和增加复杂度。因为这里的单词可能对应多个序号，一个序号可以对应多个单词的情况，使用HashMap<String,List<Integer>>来记录单词和序号的对应关系，使用ArrayList<String>来保存序号与节点(一个节点包含多个单词)的关系。

这里还需要涉及到了利用dfs求有向图是否存在cycle和判断有向图是否有多个root的一些操作。

Outcast中学到了一个操作。使用一个结果数组来保存每次操作的值，计算点到别的点的距离时就不用填一个N*N的表，只要填一个n*n/2的表就行了。

以上就是这次作业的收获。感觉难度适中，但是挺有启发性的。主要是autograder要求好高，自己又不清楚它的要求，导致重复提交了好多次。





















。
