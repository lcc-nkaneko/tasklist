USE tasklist;
DROP TABLE IF EXISTS task;

CREATE TABLE task
(
  id           INT(10),
  task_name     VARCHAR(50)
);

INSERT INTO task (id, task_name) VALUES (1, "DB設計");
INSERT INTO task (id, task_name) VALUES (2, "基本設計");