package com.devcourse.voucher.domain.customer.persistence;

import com.devcourse.voucher.domain.customer.entity.Customer;
import com.devcourse.voucher.domain.customer.entity.CustomerRepository;
import lombok.val;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

public class FileCustomerRepository implements CustomerRepository {

    private final String path;
    private final File directory;

    public FileCustomerRepository(String path) {
        val directory = new File(path);

        if (!directory.exists()) {
            directory.mkdir();
        }

        this.path = path;
        this.directory = directory;
    }

    @Override
    public List<Customer> findAll() {
        return readCustomers();
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return readCustomers().stream().filter(it -> it.getId().equals(id)).findFirst();
    }

    @Override
    public Customer save(Customer customer) {
        try (
                val fos = new FileOutputStream(new StringJoiner("/", path, ".dat").add(customer.getId().toString()).toString());
                val oos = new ObjectOutputStream(fos);
        ) {
            checkArgument(findAll().stream().noneMatch(it -> it.getEmail().equals(customer.getEmail())), "중복된 이메일");

            oos.writeObject(customer);
            oos.flush();

            return customer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Customer> readCustomers() {
        val files = directory.listFiles();

        if (Objects.isNull(files)) {
            return Collections.emptyList();
        }

        return Stream.of(files)
                .map(file -> readCustomer(file.getName()))
                .filter(Objects::nonNull)
                .toList();
    }

    private Customer readCustomer(String fileName) {
        try (
                val fis = new FileInputStream(new StringJoiner("/", path, fileName).toString());
                val ois = new ObjectInputStream(fis)
        ) {
            return (Customer) ois.readObject();
        } catch (EOFException e) {
            return null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
