package com.example.grammatisch.grammar;

import com.example.grammatisch.astregex.RegexStep;

interface Element {
    RegexStep toRegex();
}
