package com.example.voebb.service;

import com.example.voebb.model.entity.Language;

public interface LanguageService {

    Language findOrCreate(String name);
}
