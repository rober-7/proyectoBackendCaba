document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('producto-form');
    const productosContainer = document.getElementById('productos-container');

    // Cargar productos al iniciar
    cargarProductos();

    // Manejar envío del formulario
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const producto = {
            nombre: document.getElementById('nombre').value,
            precio: parseFloat(document.getElementById('precio').value),
            descripcion: document.getElementById('descripcion').value,
            imagenUrl: document.getElementById('imagenUrl').value,
            stock: parseInt(document.getElementById('stock').value),
            categoria: document.getElementById('categoria').value
        };

        try {
            const response = await fetch('/api/productos', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(producto)
            });

            if (response.ok) {
                form.reset();
                await cargarProductos();
                // Desplazarse a la lista de productos
                document.querySelector('.list-section').scrollIntoView({
                    behavior: 'smooth'
                });
            }
        } catch (error) {
            console.error('Error al crear producto:', error);
            alert('Error al crear el producto');
        }
    });

    // Función para cargar y mostrar productos
    async function cargarProductos() {
        try {
            const response = await fetch('/api/productos');
            const productos = await response.json();

            productosContainer.innerHTML = '';

            if (productos.length === 0) {
                productosContainer.innerHTML = '<p class="no-products">No hay productos registrados</p>';
                return;
            }

            // En la función cargarProductos() del app.js
            productos.forEach(producto => {
                const productoItem = document.createElement('div');
                productoItem.className = 'producto-item';

                productoItem.innerHTML = `
                    <div class="producto-imagen-container">
                        ${producto.imagenUrl ?
                            `<img src="${producto.imagenUrl}" alt="${producto.nombre}" class="producto-imagen"
                            onerror="this.src='/img/placeholder.png'">` :
                            '<div class="imagen-placeholder">Sin imagen</div>'}
                    </div>
                    <div class="producto-info">
                        <h3>${producto.nombre}</h3>
                        <p><strong>Categoría:</strong> ${producto.categoria || 'N/A'}</p>
                        <p><strong>Precio:</strong> $${producto.precio.toFixed(2)}</p>
                        <p><strong>Stock:</strong> ${producto.stock}</p>
                        <p class="descripcion">${producto.descripcion || 'Sin descripción'}</p>
                    </div>
                    <button class="delete-btn" data-id="${producto.id}">Eliminar</button>
                `;

                productosContainer.appendChild(productoItem);
            });

            // Agregar eventos a los botones de eliminar
            document.querySelectorAll('.delete-btn').forEach(btn => {
                btn.addEventListener('click', async () => {
                    if (confirm('¿Estás seguro de eliminar este producto?')) {
                        const id = btn.getAttribute('data-id');
                        await eliminarProducto(id);
                        await cargarProductos();
                    }
                });
            });
        } catch (error) {
            console.error('Error al cargar productos:', error);
            productosContainer.innerHTML = '<p class="error-message">Error al cargar los productos</p>';
        }
    }

    // Función para eliminar producto
    async function eliminarProducto(id) {
        try {
            const response = await fetch(`/api/productos/${id}`, {
                method: 'DELETE'
            });

            if (!response.ok) {
                throw new Error('Error al eliminar');
            }
        } catch (error) {
            console.error('Error al eliminar producto:', error);
            alert('Error al eliminar el producto');
        }
    }
});