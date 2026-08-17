package com.labs.systemdesign.exercise07pagination;

import java.util.List;

/** Response shape for cursor pagination: the items plus the next cursor to pass back. */
public record CursorPage<T>(List<T> items, Long nextCursor) {}
