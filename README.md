# 🚀 Multi-threaded Merge Sort with Client-Server Architecture

This project implements and compares different sorting strategies, including:
- Java's built-in `Arrays.sort()`
- Custom single-threaded recursive merge sort
- Custom **multi-threaded merge sort**
- A **client-server** setup where a client sends an array to a server, which then sorts it in parallel using multiple threads

---

## 🧠 Why It's Cool

- 💡 Combines **concurrent programming** with **network communication**
- ⚙️ Explores the performance trade-offs between **single-threaded vs multi-threaded** merge sort
- 🧵 Uses Java's `Phaser` to manage thread synchronization
- 🧪 Benchmarks sorting implementations with real timing
- 🌐 Demonstrates distributed sorting by sending arrays over TCP

---

## 🧩 Components Overview

| File/Class | Purpose |
|------------|---------|
| `Main.java` | Tests all 3 local sorting strategies and prints their timing |
| `MergeSort.java` | Implements both single-threaded and multi-threaded merge sort |
| `Client.java` | Connects to a server, sends a large array to be sorted remotely |
| `Server.java` | Receives the array, spawns multiple `SThread` instances to sort it |
| `SThread.java` | Worker thread that sorts a partition of the array using `Arrays.sort()` |

---

## ⚙️ How to Run

### 1. **Local Benchmark (Main class)**

```bash
javac com/company/*.java
java com.company.Main
```
This will:

- Generate a random array
- Sort it using Java's built-in Arrays.sort() **Dual-Pivot Quicksort & TimSort**
- Sort it using your custom single-threaded merge sort
- Sort it using your custom multi-threaded merge sort

### 2. **Client-Server Sort**

🖥️ On one terminal, start the server:
```bash
java com.company.Server
```
🖥️ On another terminal, run the client:
```bash
java com.company.Client
```
This will:

- Generate an array of 10,000 integers on the client
- Send it to the server
- The server will sort it using SThreads and print the sorted result

---

## 📊 Class Diagram

![image](https://github.com/user-attachments/assets/80df8bf1-f980-46cc-8946-89ae5dcbdb3d)

---

## 🔧 Technologies & Concepts

- **Java Thread**s & Thread subclassing
- **Phaser** for synchronization
- **Socket programming** (`ServerSocket`, `Socket`, `DataInputStream`, `DataOutputStream`)
- **Merge Sort algorithm**
- **Benchmarking** using `System.currentTimeMillis()` and `System.nanoTime()`

---

## 🛠 Requirements

- Java JDK 8 or higher
- A terminal or IDE like IntelliJ or Eclipse

---

## 📈 Future Enhancements
We will need to make sure we use the exam same sort accross for comparison, `Arrays.sort(int[])` uses **Dual-Pivot Quicksort** and `Arrays.sort(Object[])` uses **TimSort**
