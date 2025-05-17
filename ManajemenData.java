package practicum_akhir;

import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;


public class ManajemenData {
    private static HashMap<String, Student> mahasiswaMap = new HashMap<>();
    private static BST bst = new BST();

    private static Scanner scanner = new Scanner(System.in);

    // Kelas untuk node BST
    static class BSTNode {
        Student student;
        BSTNode left, right;

        BSTNode(Student student) {
            this.student = student;
            left = right = null;
        }
    }


    // Kelas untuk BST
    static class BST {
        BSTNode root;

        // Insert ke BST
        void insert(Student student) {
            root = insertRec(root, student);
        }

        private BSTNode insertRec(BSTNode root, Student student) {
            if (root == null) {
                root = new BSTNode(student);
                return root;
            }

            if (student.nim.compareTo(root.student.nim) < 0)
                root.left = insertRec(root.left, student);
            else if (student.nim.compareTo(root.student.nim) > 0)
                root.right = insertRec(root.right, student);

            return root;


        }


        // Traversal inorder untuk menampilkan data terurut
        void inorder() {
            inorderRec(root);

        }

        private void inorderRec(BSTNode root) {
            if (root != null) {
                inorderRec(root.left);
                System.out.println(root.student);
                inorderRec(root.right);
            }

        }
    }

    // Kelas data mahasiswa
    static class Student {
        String nim;
        String nama;
        double ipk;

        public Student(String nim, String nama, double ipk) {
            this.nim = nim;
            this.nama = nama;
            this.ipk = ipk;
        }

        @Override
        public String toString() {
            return "NIM: " + nim + ", Nama: " + nama + ", IPK: " + ipk;
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Sistem Manajemen Data Mahasiswa ===");
            System.out.println("1. Tambah Mahasiswa");
            System.out.println("2. Cari Mahasiswa");
            System.out.println("3. Hapus Mahasiswa");
            System.out.println("4. Tampilkan Semua Mahasiswa");
            System.out.println("5. Tampilkan Data Terurut (BST)");

            System.out.println("6. Keluar");
            System.out.print("Pilih menu: ");


            try {
                int pilihan = scanner.nextInt();
                scanner.nextLine(); 

                switch (pilihan) {
                    case 1:
                        tambahMahasiswa();
                        break;
                    case 2:
                        cariMahasiswa();
                        break;
                    case 3:
                        hapusMahasiswa();
                        break;
                    case 4:
                        tampilkanSemuaMahasiswa();
                        break;
                    case 5:
                        tampilkanTerurut();
                        break;
                    case 6:
                        System.out.println("Keluar dari sistem...");
                        System.exit(0);
                    default:
                        System.out.println("Pilihan tidak valid!");

                }


            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid! Harap masukkan angka.");
                scanner.nextLine(); // Membersihkan input yang salah
            }

        }
    }



    private static void tambahMahasiswa() {
        System.out.print("\nMasukkan NIM: ");
        String nim = scanner.nextLine();
        
        if (mahasiswaMap.containsKey(nim)) {
            System.out.println("NIM sudah terdaftar");
            return;
        }
        
        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();
        
        double ipk = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print("Masukkan IPK: ");
            try {
                ipk = scanner.nextDouble();
                scanner.nextLine();
                if (ipk < 0 || ipk > 4.0) {
                    throw new InputMismatchException();
                }
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("IPK harus angka antara 0.0 - 4.0");
                scanner.nextLine();
            }
        }

        Student newStudent = new Student(nim, nama, ipk);
        mahasiswaMap.put(nim, newStudent);
        bst.insert(newStudent);
        System.out.println("Mahasiswa berhasil ditambahkan");


    }

    private static void cariMahasiswa() {
        System.out.print("\nMasukkan NIM yang dicari: ");
        String nim = scanner.nextLine();
        
        Student student = mahasiswaMap.get(nim);
        if (student != null) {
            System.out.println("Data ditemukan: " + student);
        } else {
            System.out.println("Mahasiswa dengan NIM " + nim + " tidak ditemukan");
        }
    }

    private static void hapusMahasiswa() {
        System.out.print("\nMasukkan NIM yang akan dihapus: ");
        String nim = scanner.nextLine();
        
        if (mahasiswaMap.containsKey(nim)) {
            mahasiswaMap.remove(nim);
      
            System.out.println("Mahasiswa dengan NIM " + nim + " berhasil dihapus");
        } else {
            System.out.println("Mahasiswa dengan NIM " + nim + " tidak ditemukan");
        }
    }



    private static void tampilkanSemuaMahasiswa() {
        System.out.println("\n=== Daftar Seluruh Mahasiswa ===");
        if (mahasiswaMap.isEmpty()) {
            System.out.println("Belum ada data mahasiswa!");
            return;
        }
        
        for (Student student : mahasiswaMap.values()) {
            System.out.println(student);
        }
    }

    private static void tampilkanTerurut() {
        System.out.println("\n=== Daftar Mahasiswa ===");
        if (bst.root == null) {
            System.out.println("Belum ada data mahasiswa");
            return;
        }
        bst.inorder();
    }



}

