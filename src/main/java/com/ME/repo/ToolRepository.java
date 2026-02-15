package com.ME.repo;

import com.ME.entity.Tool;

import java.util.List;

public interface ToolRepository {
    List<Tool> readTool();
    void saveTool(Tool tool);
    void updateTool(Tool tool);
    void deleteTool(Tool tool);
}
