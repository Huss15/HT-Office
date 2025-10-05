package com.hassuna.tech.htoffice.base.remote.paylaod;

import java.util.List;

public record PagePayload<T>(List<T> content, boolean last) {}
