//
// Copyright (C) 2006 United States Government as represented by the
// Administrator of the National Aeronautics and Space Administration
// (NASA).  All Rights Reserved.
//
// This software is distributed under the NASA Open Source Agreement
// (NOSA), version 1.3.  The NOSA has been approved by the Open Source
// Initiative.  See the file NOSA-1.3-JPF at the top of the distribution
// directory tree for the complete NOSA document.
//
// THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY OF ANY
// KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT
// LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL CONFORM TO
// SPECIFICATIONS, ANY IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
// A PARTICULAR PURPOSE, OR FREEDOM FROM INFRINGEMENT, ANY WARRANTY THAT
// THE SUBJECT SOFTWARE WILL BE ERROR FREE, OR ANY WARRANTY THAT
// DOCUMENTATION, IF PROVIDED, WILL CONFORM TO THE SUBJECT SOFTWARE.
//

public class DiningPhil {

  static class Fork {
  }

  static class Philosopher extends Thread {

    Fork left;
    Fork right;
    int number;

    public Philosopher(Fork left, Fork right, int number) {
      this.left = left;
      this.right = right;
      this.number = number;
      start();
    }

    public void run() {
      // think!
      if (number == (N-1)) {
              synchronized (right) {
                      synchronized (left) {
                              // eat!
                      }
              }
      }
        else {
      synchronized (left) {
        synchronized (right) {
          // eat!
        }
      }
        }
    }
  }

  static final int N = 5;

  public static void main(String[] args) {
    Fork[] forks = new Fork[N];
    for (int i = 0; i < N; i++) {
      forks[i] = new Fork();
    }
    for (int i = 0; i < N; i++) {
      new Philosopher(forks[i], forks[(i + 1) % N], i);
    }
  }
}


/*
Lock Dependancy Graph (T1 refers to thread 1, F1ca refers to lock 1ca for that fork instance).
As can be seen. The lock starts because once T1 wants to obtain the lock on 
F1ce it can't, because T5 has that lock. And since they now are all waiting
for the lock to their right, but each thread has a lock on their left, it can't 
be resolved. Thus a deadlock is created.

      T1
    /    \
  F1ce  F1ca
   |       |
  T5      T2
  |        |
  F1cd     F1cb
    \      /
    T4   T3
      \ /
      F1cc

*/

// Trace after changes
/*
JavaPathfinder core system v8.0 (rev 3408119d115e539956a3d920e22e856e05bb9d23) - (C) 2005-2014 United States Government. All rights reserved.


====================================================== system under test
DiningPhil.main()

====================================================== search started: 5/4/23 2:27 PM

====================================================== results
no errors detected

====================================================== statistics
elapsed time:       00:00:20
states:             new=80013,visited=270944,backtracked=350957,end=51
search:             maxDepth=45,constraints=0
choice generators:  thread=80013 (signal=0,lock=30797,sharedRef=22782,threadApi=5,reschedule=26429), data=0
heap:               new=44710,released=551053,maxLive=489,gcCycles=327833
instructions:       3110154
max memory:         496MB
loaded code:        classes=85,methods=1817

====================================================== search finished: 5/4/23 2:28 PM
*/

// Original trace before changes
/*
JavaPathfinder core system v8.0 (rev 3408119d115e539956a3d920e22e856e05bb9d23) - (C) 2005-2014 United States Government. All rights reserved.


====================================================== system under test
DiningPhil.main()

====================================================== search started: 5/4/23 2:25 PM

====================================================== error 1
gov.nasa.jpf.vm.NotDeadlockedProperty
deadlock encountered:
  thread DiningPhil$Philosopher:{id:1,name:Thread-1,status:BLOCKED,priority:5,isDaemon:false,lockCount:0,suspendCount:0}
  thread DiningPhil$Philosopher:{id:2,name:Thread-2,status:BLOCKED,priority:5,isDaemon:false,lockCount:0,suspendCount:0}
  thread DiningPhil$Philosopher:{id:3,name:Thread-3,status:BLOCKED,priority:5,isDaemon:false,lockCount:0,suspendCount:0}
  thread DiningPhil$Philosopher:{id:4,name:Thread-4,status:BLOCKED,priority:5,isDaemon:false,lockCount:0,suspendCount:0}
  thread DiningPhil$Philosopher:{id:5,name:Thread-5,status:BLOCKED,priority:5,isDaemon:false,lockCount:0,suspendCount:0}


====================================================== trace #1
------------------------------------------------------ transition #0 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"ROOT" ,1/1,isCascaded:false}
      [6345 insn w/o sources]
  DiningPhil.java:50             : Fork[] forks = new Fork[N];
  DiningPhil.java:51             : for (int i = 0; i < N; i++) {
  DiningPhil.java:52             : forks[i] = new Fork();
  DiningPhil.java:23             : static class Fork {
      [1 insn w/o sources]
  DiningPhil.java:23             : static class Fork {
  DiningPhil.java:52             : forks[i] = new Fork();
  DiningPhil.java:51             : for (int i = 0; i < N; i++) {
  DiningPhil.java:52             : forks[i] = new Fork();
  DiningPhil.java:23             : static class Fork {
      [1 insn w/o sources]
  DiningPhil.java:23             : static class Fork {
  DiningPhil.java:52             : forks[i] = new Fork();
  DiningPhil.java:51             : for (int i = 0; i < N; i++) {
  DiningPhil.java:52             : forks[i] = new Fork();
  DiningPhil.java:23             : static class Fork {
      [1 insn w/o sources]
  DiningPhil.java:23             : static class Fork {
  DiningPhil.java:52             : forks[i] = new Fork();
  DiningPhil.java:51             : for (int i = 0; i < N; i++) {
  DiningPhil.java:52             : forks[i] = new Fork();
  DiningPhil.java:23             : static class Fork {
      [1 insn w/o sources]
  DiningPhil.java:23             : static class Fork {
  DiningPhil.java:52             : forks[i] = new Fork();
  DiningPhil.java:51             : for (int i = 0; i < N; i++) {
  DiningPhil.java:52             : forks[i] = new Fork();
  DiningPhil.java:23             : static class Fork {
      [1 insn w/o sources]
  DiningPhil.java:23             : static class Fork {
  DiningPhil.java:52             : forks[i] = new Fork();
  DiningPhil.java:51             : for (int i = 0; i < N; i++) {
  DiningPhil.java:55             : new Philosopher(forks[i], forks[(i + 1) % N]);
  DiningPhil.java:31             : public Philosopher(Fork left, Fork right) {
      [145 insn w/o sources]
  DiningPhil.java:32             : this.left = left;
  DiningPhil.java:33             : this.right = right;
  DiningPhil.java:34             : start();
      [1 insn w/o sources]
------------------------------------------------------ transition #1 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"START" ,1/2,isCascaded:false}
      [2 insn w/o sources]
  DiningPhil.java:35             : }
  DiningPhil.java:55             : new Philosopher(forks[i], forks[(i + 1) % N]);
  DiningPhil.java:54             : for (int i = 0; i < N; i++) {
  DiningPhil.java:55             : new Philosopher(forks[i], forks[(i + 1) % N]);
  DiningPhil.java:31             : public Philosopher(Fork left, Fork right) {
      [27 insn w/o sources]
------------------------------------------------------ transition #2 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,1/2,isCascaded:false}
      [119 insn w/o sources]
  DiningPhil.java:32             : this.left = left;
  DiningPhil.java:33             : this.right = right;
------------------------------------------------------ transition #3 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"EXPOSE" ,1/2,isCascaded:false}
  DiningPhil.java:33             : this.right = right;
  DiningPhil.java:34             : start();
      [1 insn w/o sources]
------------------------------------------------------ transition #4 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"START" ,1/3,isCascaded:false}
      [2 insn w/o sources]
  DiningPhil.java:35             : }
  DiningPhil.java:55             : new Philosopher(forks[i], forks[(i + 1) % N]);
  DiningPhil.java:54             : for (int i = 0; i < N; i++) {
  DiningPhil.java:55             : new Philosopher(forks[i], forks[(i + 1) % N]);
  DiningPhil.java:31             : public Philosopher(Fork left, Fork right) {
      [27 insn w/o sources]
------------------------------------------------------ transition #5 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,1/3,isCascaded:false}
      [119 insn w/o sources]
  DiningPhil.java:32             : this.left = left;
  DiningPhil.java:33             : this.right = right;
------------------------------------------------------ transition #6 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"EXPOSE" ,1/3,isCascaded:false}
  DiningPhil.java:33             : this.right = right;
  DiningPhil.java:34             : start();
      [1 insn w/o sources]
------------------------------------------------------ transition #7 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"START" ,1/4,isCascaded:false}
      [2 insn w/o sources]
  DiningPhil.java:35             : }
  DiningPhil.java:55             : new Philosopher(forks[i], forks[(i + 1) % N]);
  DiningPhil.java:54             : for (int i = 0; i < N; i++) {
  DiningPhil.java:55             : new Philosopher(forks[i], forks[(i + 1) % N]);
  DiningPhil.java:31             : public Philosopher(Fork left, Fork right) {
      [27 insn w/o sources]
------------------------------------------------------ transition #8 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,1/4,isCascaded:false}
      [119 insn w/o sources]
  DiningPhil.java:32             : this.left = left;
  DiningPhil.java:33             : this.right = right;
------------------------------------------------------ transition #9 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"EXPOSE" ,1/4,isCascaded:false}
  DiningPhil.java:33             : this.right = right;
  DiningPhil.java:34             : start();
      [1 insn w/o sources]
------------------------------------------------------ transition #10 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"START" ,1/5,isCascaded:false}
      [2 insn w/o sources]
  DiningPhil.java:35             : }
  DiningPhil.java:55             : new Philosopher(forks[i], forks[(i + 1) % N]);
  DiningPhil.java:54             : for (int i = 0; i < N; i++) {
  DiningPhil.java:55             : new Philosopher(forks[i], forks[(i + 1) % N]);
  DiningPhil.java:31             : public Philosopher(Fork left, Fork right) {
      [27 insn w/o sources]
------------------------------------------------------ transition #11 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,1/5,isCascaded:false}
      [119 insn w/o sources]
  DiningPhil.java:32             : this.left = left;
  DiningPhil.java:33             : this.right = right;
  DiningPhil.java:34             : start();
      [1 insn w/o sources]
------------------------------------------------------ transition #12 thread: 0
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"START" ,1/6,isCascaded:false}
      [2 insn w/o sources]
  DiningPhil.java:35             : }
  DiningPhil.java:55             : new Philosopher(forks[i], forks[(i + 1) % N]);
  DiningPhil.java:54             : for (int i = 0; i < N; i++) {
  DiningPhil.java:57             : }
  DiningPhil.java:3              : // Copyright (C) 2006 United States Government as represented by the
------------------------------------------------------ transition #13 thread: 1
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"TERMINATE" ,1/5,isCascaded:false}
      [1 insn w/o sources]
  DiningPhil.java:1              :
  DiningPhil.java:39             : synchronized (left) {
------------------------------------------------------ transition #14 thread: 1
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,1/5,isCascaded:false}
  DiningPhil.java:39             : synchronized (left) {
  DiningPhil.java:40             : synchronized (right) {
------------------------------------------------------ transition #15 thread: 2
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,2/5,isCascaded:false}
      [1 insn w/o sources]
  DiningPhil.java:1              :
  DiningPhil.java:39             : synchronized (left) {
------------------------------------------------------ transition #16 thread: 2
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,2/5,isCascaded:false}
  DiningPhil.java:39             : synchronized (left) {
  DiningPhil.java:40             : synchronized (right) {
------------------------------------------------------ transition #17 thread: 3
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,2/4,isCascaded:false}
      [1 insn w/o sources]
  DiningPhil.java:1              :
  DiningPhil.java:39             : synchronized (left) {
------------------------------------------------------ transition #18 thread: 3
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,2/4,isCascaded:false}
  DiningPhil.java:39             : synchronized (left) {
  DiningPhil.java:40             : synchronized (right) {
------------------------------------------------------ transition #19 thread: 4
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,2/3,isCascaded:false}
      [1 insn w/o sources]
  DiningPhil.java:1              :
  DiningPhil.java:39             : synchronized (left) {
------------------------------------------------------ transition #20 thread: 4
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,2/3,isCascaded:false}
  DiningPhil.java:39             : synchronized (left) {
  DiningPhil.java:40             : synchronized (right) {
------------------------------------------------------ transition #21 thread: 5
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,2/2,isCascaded:false}
      [1 insn w/o sources]
  DiningPhil.java:1              :
  DiningPhil.java:39             : synchronized (left) {
------------------------------------------------------ transition #22 thread: 5
gov.nasa.jpf.vm.choice.ThreadChoiceFromSet {id:"LOCK" ,2/2,isCascaded:false}
  DiningPhil.java:39             : synchronized (left) {
  DiningPhil.java:40             : synchronized (right) {

====================================================== snapshot #1
thread DiningPhil$Philosopher:{id:1,name:Thread-1,status:BLOCKED,priority:5,isDaemon:false,lockCount:0,suspendCount:0}
  owned locks:DiningPhil$Fork@1ca
  blocked on: DiningPhil$Fork@1cb
  call stack:
        at DiningPhil$Philosopher.run(DiningPhil.java:40)

thread DiningPhil$Philosopher:{id:2,name:Thread-2,status:BLOCKED,priority:5,isDaemon:false,lockCount:0,suspendCount:0}
  owned locks:DiningPhil$Fork@1cb
  blocked on: DiningPhil$Fork@1cc
  call stack:
        at DiningPhil$Philosopher.run(DiningPhil.java:40)

thread DiningPhil$Philosopher:{id:3,name:Thread-3,status:BLOCKED,priority:5,isDaemon:false,lockCount:0,suspendCount:0}
  owned locks:DiningPhil$Fork@1cc
  blocked on: DiningPhil$Fork@1cd
  call stack:
        at DiningPhil$Philosopher.run(DiningPhil.java:40)

thread DiningPhil$Philosopher:{id:4,name:Thread-4,status:BLOCKED,priority:5,isDaemon:false,lockCount:0,suspendCount:0}
  owned locks:DiningPhil$Fork@1cd
  blocked on: DiningPhil$Fork@1ce
  call stack:
        at DiningPhil$Philosopher.run(DiningPhil.java:40)

thread DiningPhil$Philosopher:{id:5,name:Thread-5,status:BLOCKED,priority:5,isDaemon:false,lockCount:0,suspendCount:0}
  owned locks:DiningPhil$Fork@1ce
  blocked on: DiningPhil$Fork@1ca
  call stack:
        at DiningPhil$Philosopher.run(DiningPhil.java:40)


====================================================== results
error #1: gov.nasa.jpf.vm.NotDeadlockedProperty "deadlock encountered:   thread DiningPhil$Philosop..."

====================================================== statistics
elapsed time:       00:00:00
states:             new=2535,visited=5671,backtracked=8183,end=29
search:             maxDepth=29,constraints=0
choice generators:  thread=2534 (signal=0,lock=1608,sharedRef=3,threadApi=5,reschedule=918), data=0
heap:               new=508,released=19611,maxLive=489,gcCycles=8206
instructions:       58712
max memory:         304MB
loaded code:        classes=85,methods=1817
*/

