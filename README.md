## Algorithm
这个项目是Coursera Algorithm的practise assignment。我是直接上的Part 2.所以先从Part2开始，有必要会看一些Part 1的内容。

### Week1 Wordnet
题目其实就是去构建单词之间的从属关系。[说明](http://coursera.cs.princeton.edu/algs4/assignments/wordnet.html)

![](http://ovkwd4vse.bkt.clouddn.com/markdown-img-paste-20180319153459179.png)

SAP主要的算法点就是要寻找到有向图中两个节点到共有root节点的最短路径。这里使用的方法是BFS(宽度优先搜索)。通过BFS去查找到节点的上层root，并记录下root到节点的距离，这样就可以比较两个节点的多个相同root并找到最小值。

WordNet的算法点就是如何选择合理的数据结构存储节点关系。这里考虑到读取文件时要大量的增加节点，查询之前是否存在过这个节点，所以使用了HashMap。HashMap可以达到O(1)的查找和增加复杂度。因为这里的单词可能对应多个序号，一个序号可以对应多个单词的情况，使用HashMap<String,List<Integer>>来记录单词和序号的对应关系，使用ArrayList<String>来保存序号与节点(一个节点包含多个单词)的关系。

这里还需要涉及到了利用dfs求有向图是否存在cycle和判断有向图是否有多个root的一些操作。

Outcast中学到了一个操作。使用一个结果数组来保存每次操作的值，计算点到别的点的距离时就不用填一个N\*N的表，只要填一个n*n/2的表就行了。

以上就是这次作业的收获。感觉难度适中，但是挺有启发性的。主要是autograder要求好高，自己又不清楚它的要求，导致重复提交了好多次。

### Week2 SeamCarver
这周的作业有点意思，有一点点图像处理的知识。主要的思想就是从顶到下(或者从左到右时)每个点只可能走(x-1,y+1),(x,y+1),(x+1,y+1)这三个点，那么针对这三个点进行relax就行了。最最后去统计所有的路径中weight最小的。


### Week3 BaseBall Elimination
这周的作业有点难度，我自己是没有想到的。但是出题者给出了提示，给了我们这样一张针对team b的network的设计。

![](http://ovkwd4vse.bkt.clouddn.com/markdown-img-paste-20180401110427344.png)

这样就给了我们一些思路，$g_{ij}$ 就是我们的流了。通过ForkFoldson算法我们可以得到这个network的最大流，也就是说，通过比较S点的流出和network的最大流的大小我们就可以知道，如果S点流出大于最大流，那么说明存在队伍a，就算b后面的比赛全赢，也会被eliminate


### Week4 boggle
Boggle这个问题很有意思，是一种经常出现的智力题。在一个board里面去寻找给定dictionary包含的单词，这个想法和之前做过的LeetCode上的题很像。就是进行遍历+dfs。
主要的考查是数据结构的选择：为了方便查询dict中的String，这里用了Trie树，没有用TST是因为用空间换时间，Trie树有更高的查询效率。为了避免重复单词，用HashSet来进行存储。














。
