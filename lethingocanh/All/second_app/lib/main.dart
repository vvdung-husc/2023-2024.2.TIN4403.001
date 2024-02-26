import 'package:flutter/material.dart';

void main() {
  runApp( 
    MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('test'),
        ),
        body: const Center(
          child: Text('hello'),
        ),
      ),
    ),
  );
}
