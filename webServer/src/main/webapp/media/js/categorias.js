document.addEventListener("DOMContentLoaded", async () => {
    const lista = document.getElementById('lista');

    try {
        const res = await fetch("/webServer/datosJson?categorias=all");

        if (!res.ok) {
            throw new Error(`Error HTTP: ${res.status}`);
        }

        const cat = await res.json();
        console.log("Categorías:", cat);

        // Limpio la lista actual
        lista.innerHTML = '';

        // Recorro el array y agrego <li> por cada elemento
        cat.forEach(element => {
            const li = document.createElement('li');
            li.className = 'list-group-item';
            li.textContent = element;
            lista.appendChild(li);
            li.onclick = () => {
                window.location.href = `/webServer/eventos?categoria=${element}`;
            };
        });

    } catch (error) {
        console.error("Error al cargar las categorías:", error);
        lista.innerHTML = '<li class="list-group-item text-danger">Error al cargar datos</li>';
    }
});