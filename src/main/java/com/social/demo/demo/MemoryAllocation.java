package com.social.demo.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Process {
    int id;
    int size;
    int startAddress;

    public Process(int id, int size) {
        this.id = id;
        this.size = size;
    }
}

class MemoryBlock {
    int startAddress;
    int size;

    public MemoryBlock(int startAddress, int size) {
        this.startAddress = startAddress;
        this.size = size;
    }
}

public class MemoryAllocation {
    List<MemoryBlock> freeBlocks;
    List<Process> processes;
    int memorySize;

    public MemoryAllocation(int memorySize) {
        freeBlocks = new ArrayList<>();
        processes = new ArrayList<>();
        this.memorySize = memorySize;
    }

    // 初始化空闲分区表
    public void initializeFreeBlocks() {
        freeBlocks.add(new MemoryBlock(0, memorySize));
    }

    // 首次适应算法
    public boolean allocateFirstFit(Process process) {
        for (MemoryBlock block : freeBlocks) {
            if (block.size >= process.size) {
                block.size -= process.size;
                process.startAddress = block.startAddress;

                if (block.size > 0) {
                    block.startAddress += process.size;
                } else {
                    freeBlocks.remove(block);
                }

                return true;
            }
        }

        return false;
    }

    // 回收内存
    public void deallocateMemory(int processId) {
        Process process = null;

        for (Process p : processes) {
            if (p.id == processId) {
                process = p;
                break;
            }
        }

        if (process != null) {
            MemoryBlock newBlock = new MemoryBlock(process.startAddress, process.size);

            // 使用迭代器遍历freeBlocks列表
            Iterator<MemoryBlock> iterator = freeBlocks.iterator();
            boolean merged = false;

            while (iterator.hasNext()) {
                MemoryBlock block = iterator.next();

                if (block.startAddress > newBlock.startAddress) {
                    freeBlocks.add(freeBlocks.indexOf(block), newBlock);
                    merged = true;
                    break;
                } else if (block.startAddress + block.size == newBlock.startAddress) {
                    block.size += newBlock.size;
                    merged = true;

                    // 检查是否可以继续合并后续的空闲块
                    mergeAdjacentFreeBlocks(iterator);
                    break;
                }
            }

            if (!merged) {
                freeBlocks.add(newBlock);
            }

            processes.remove(process);
        }
    }

    // 合并相邻的空闲块
    private void mergeAdjacentFreeBlocks(Iterator<MemoryBlock> iterator) {
        MemoryBlock currentBlock = iterator.next();
        while (iterator.hasNext()) {
            MemoryBlock nextBlock = iterator.next();
            if (currentBlock.startAddress + currentBlock.size == nextBlock.startAddress) {
                currentBlock.size += nextBlock.size;
                iterator.remove();  // 移除已经合并的空闲块
            } else {
                break;  // 已经不能继续合并，退出循环
            }
        }
    }

    // 显示内存分配和回收后的结果
    public void displayMemoryAllocation() {
        System.out.println("空闲内存块情况:");
        System.out.println("------------------");

        if (freeBlocks.isEmpty()) {
            System.out.println("没有空闲内存块.");
        } else {
            for (MemoryBlock block : freeBlocks) {
                System.out.println("空闲内存块开始地址: " + block.startAddress);
                System.out.println("大小: " + block.size);
                System.out.println("------------------");
            }
        }
    }

    public static void main(String[] args) {
        int memorySize = 1024; // 模拟内存大小为1024
        MemoryAllocation memoryAllocation = new MemoryAllocation(memorySize);
        memoryAllocation.initializeFreeBlocks();

        // 创建5个进程
        memoryAllocation.processes.add(new Process(1, 100));
        memoryAllocation.processes.add(new Process(2, 200));
        memoryAllocation.processes.add(new Process(3, 50));
        memoryAllocation.processes.add(new Process(4, 300));
        memoryAllocation.processes.add(new Process(5, 150));

        // 使用首次适应算法进行内存分配
        for (Process process : memoryAllocation.processes) {
            boolean allocated = memoryAllocation.allocateFirstFit(process);

            if (allocated) {
                System.out.println("进程 " + process.id + " 已经分配到内存块起始地址为: " + process.startAddress);
            } else {
                System.out.println("进程 " + process.id + " 内存块分配失败.");
            }
        }

        memoryAllocation.displayMemoryAllocation();

        // 结束一些进程，回收内存
        memoryAllocation.deallocateMemory(2);
        memoryAllocation.deallocateMemory(4);
        memoryAllocation.deallocateMemory(5);

        memoryAllocation.displayMemoryAllocation();
    }
}
