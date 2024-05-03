function createNewTask(e) {
  e.preventDefault();

  let data = {};
  let fields = e.target.elements;
  $.each(fields, function (i, field) {
    if (field.value) data[field.name] = field.value;
  });

  $.ajax({
    url: "/api/epic",
    type: "POST",
    dataType: "json",
    contentType: "application/json",
    data: JSON.stringify(data),
    success: function (res) {
      console.log(res);
      let html = `<div class="card task-box mb-3">
      <div class="card-body">
        <div class="dropdown float-end">
          <a class="dropdown-toggle arrow-none"
            data-bs-toggle="dropdown" aria-expanded="false"> <i
            class="mdi mdi-dots-vertical m-0 text-muted h5"></i>
          </a>
          <div class="dropdown-menu dropdown-menu-end">
            <a class="dropdown-item edittask-details"
              data-bs-toggle="modal" data-bs-target="#editTaskModal"
              onclick="populateModal(this, '${res.epicID}')">Edit</a> <a
              class="dropdown-item deletetask"
              onclick="deleteTask(this, '${res.epicID}')">Delete</a>
          </div>
        </div>
        <div id="item-${res.epicID}">
          <h6 class="font-size-15 text-dark">${data.title}</h6>
          <span class="text-muted mb-2">${new Date().toDateString()}</span>
          <span class="text-muted mb-2 float-sm-end">${data.team}</span>
          <p>${data.description}</p>
        </div>
      </div>
    </div>`;
      $("#taskModal").modal("hide");
      $("#upcoming-task").append(html);
    },
    error: function (err) {
      console.error(err);
    },
  });
}

function populateModal(e, id) {
  let fields = e.parentElement.parentElement.nextElementSibling.children;
  $("#etaskid").val(id);
  $("#etitle").val($.trim(fields[0].innerText));
  $("#eteam").val($.trim(fields[2].innerText));
  $("#edescription").val($.trim(fields[3].innerText));
}

function updateTask(e) {
  e.preventDefault();

  let data = {};
  let fields = e.target.elements;
  $.each(fields, function (i, field) {
    if (field.value) data[field.name] = field.value;
  });

  $.ajax({
    url: `/api/epic/${data.id}`,
    type: "PUT",
    dataType: "json",
    contentType: "application/json",
    data: JSON.stringify(data),
    success: function (res) {
      console.log(res);
      let elements = $("#item-" + data.id)[0].children;
      elements[0].innerText = $("#etitle").val();
      elements[2].innerText = $("#eteam").val();
      elements[3].innerText = $("#edescription").val();
      $("#editTaskModal").modal("hide");
    },
    error: function (err) {
      console.error(err);
    },
  });
}

function updateTaskStatus(e) {
  if (e[0] != "drop") return;
  let ele = e[1];
  let target = e[2];
  let id = ele.children[0].children[1].id.substring(5);
  let status = "BACKLOG";
  if (target.id.startsWith("inprogress")) status = "IN_PROGRESS";
  else if (target.id.startsWith("complete")) status = "COMPLETED";

  $.ajax({
    url: `/api/epic/${id}/${status}`,
    type: "PUT",
    dataType: "json",
    success: function (res) {
      console.log(res);
    },
    error: function (err) {
      console.error(err);
    },
  });
}

function deleteTask(e, id) {
  $.ajax({
    url: `/api/epic/${id}`,
    type: "DELETE",
    dataType: "json",
    success: function (res) {
      console.log(res);
      e.parentElement.parentElement.parentElement.parentElement.remove();
    },
    error: function (err) {
      console.error(err);
    },
  });
}

$("#newtask")[0]?.addEventListener("submit", createNewTask);
$("#edittask")[0].addEventListener("submit", updateTask);
