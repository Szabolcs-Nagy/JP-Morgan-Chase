package com.jpmc.szabolcs.utils
/**
 * Creating the [OpenClass] annotation class
 * to open some classes for mocking purposes
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class OpenClass
/**
 * Creating the [OpenForTesting] annotation class
 * Using this annotation on a class with I
 * can mak it be extendable in debug builds.
 */
@OpenClass
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting