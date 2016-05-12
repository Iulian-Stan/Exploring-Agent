# Exploring Agent

This is the improved version of the [**Reactive Agent**](https://github.com/Iulian-Stan/ReactiveAgent).
Unlike the previous one, the new agent is **intelligent**: it has a **_reasoning_** mechanism allowing him to solve 
specific problems (will be decribed later), **_field of view_** (1 cell around itself) and **_memory_** 
to store the map of the explored region.

As in the previous example the agent is placed in a two dimensional space represented by an **array** composed of:
 1. free cells
 2. cells containing artifacts 
 3. obstacles
 4. destination cells - **_new type_**

The agent is no longer able to collect artifacts. In order to score it has to move (push) the artifact into 
the destination cell (each destination cell can hold only one artifact). This recuires the ability to identify
objects on the map and to compute paths between two points. Moreother, during the path planning it has to take into 
account that the artifact can only be pushed (from behind), meaning that it becomes blocked once it reaches a corner.

## Solution

We assume that the agent knows the total number of artifacts and destinations (and they match) on the map to prevent 
redundant exploring  once the final objective is reached. 

It starts by exploring the map counting the encoutered artifacts and destination cells, once there is a pair 
it tries to find a path (on the explored region). If there is a solution, agent switches to its execution,
otherwise it continues to explore the map until a new item is found and attempts to find a new path.

The execution ends when all artifacts are moved to coresponding destination cells, or the map if fully explored and
there is no valid path for the remaining pairs.

## Interface 

comming soon
