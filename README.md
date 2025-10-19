[![Java CI with Gradle](https://github.com/codicis/gsma-sdk/actions/workflows/gradle.yml/badge.svg)](https://github.com/codicis/gsma-sdk/actions/workflows/gradle.yml)

# GSMA SDK

## Overview

The **GSMA SDK** provides a set of Java libraries that simplify integration with GSM‑based mobile services.
It includes JVM-based open standard implementation for GSMA TAP3, RAP, and more.

## Features

- **TAP-Codec** – encoding/decoding utilities for TAP 3.11 and RAP 1.05 Files.

## Getting Started

The `TapFiles` class provides a static entry point for reading TLV-encoded TAP3 files using BER decoding. It exposes
high-level methods to extract structured domain objects such as `DataInterChange`, `BatchControlInfo`, and others from a
binary file.

For instance, read the entire TAP3 interchange

```java
DataInterChange interchange = TapFiles.read(Paths.get("tapfile.dat"));
```

## TAP3 Open Standard

The GSMA provides access to
the [TAP3 Open Standard Specification](https://www.gsma.com/get-involved/working-groups/interoperability-data-specifications-and-settlement-group/standardised-b2b-interfaces-specified-by-ids/open-standards-specifications/tap3-open-standard-download-form/) via a download form. 
This specification is part of their Interoperability Data Specifications and Settlement (IDS) initiative.