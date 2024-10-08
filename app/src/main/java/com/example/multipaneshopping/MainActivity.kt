package com.example.multipaneshopping

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.multipaneshopping.ui.theme.MultiPaneShoppingTheme

data class Product(
    val name: String,
    val price: String,
    val description: String,
    val imageResId: Int
)

val products = listOf(
    Product("Pear Juice", "$3.99", "Fresh pear juice.", R.drawable.pear_juice),
    Product("Krispy Kreme Donuts", "$5.49", "Delicious glazed donuts.", R.drawable.krispy_kreme_donuts),
    Product("Apples", "$1.29/lb", "Crisp and juicy honeycrisp apples.", R.drawable.apples),
    Product("Bananas", "$0.59/lb", "Sweet ripe bananas.", R.drawable.bananas),
    Product("Sweet Tea", "$2.99", "Refreshing sweet tea.", R.drawable.sweet_tea),
    Product("Filo Dough", "$4.49", "Thin pastry dough sheets.", R.drawable.filo_dough),
    Product("Toothpaste", "$2.99", "Minty fresh toothpaste.", R.drawable.toothpaste),
    Product("Chicken Breast", "$6.99/lb", "Boneless chicken breast.", R.drawable.chicken_breast),
    Product("Breadcrumbs", "$1.99", "Fine breadcrumbs.", R.drawable.breadcrumbs),
    Product("Pasta Penne", "$1.49", "Italian penne pasta.", R.drawable.pasta_penne),
    Product("Ground Beef", "$4.99/lb", "Lean ground beef.", R.drawable.ground_beef),
    Product("Milk", "$2.49", "Fresh whole milk.", R.drawable.milk),
//    Product("Eggs", "$1.99", "A dozen large eggs.", R.drawable.eggs),
//    Product("Cheddar Cheese", "$3.49", "Sharp cheddar cheese.", R.drawable.cheddar_cheese),
//    Product("Orange Juice", "$3.29", "100% pure orange juice.", R.drawable.orange_juice),
//    Product("Cereal", "$2.99", "Crunchy oat cereal.", R.drawable.cereal),
//    Product("Bread", "$1.99", "Whole grain bread.", R.drawable.bread),
//    Product("Butter", "$2.49", "Creamy unsalted butter.", R.drawable.butter),
//    Product("Yogurt", "$0.99", "Strawberry flavored yogurt.", R.drawable.yogurt),
//    Product("Coffee", "$4.99", "Ground Arabica coffee.", R.drawable.coffee)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultiPaneShoppingTheme {
                var selectedProduct by remember { mutableStateOf<Product?>(null) }

                MainScreen(
                    products = products,
                    selectedProduct = selectedProduct,
                    onProductSelected = { product ->
                        selectedProduct = product
                    },
                    onBack = {
                        selectedProduct = null
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    products: List<Product>,
    selectedProduct: Product?,
    onProductSelected: (Product?) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Piggly Wiggly Shopping")
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.piggly_wiggly_logo),
                        contentDescription = "Piggly Wiggly Logo",
                        modifier = Modifier.size(40.dp)
                    )
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (selectedProduct == null) {
                ProductGrid(
                    products = products,
                    onProductSelected = onProductSelected,
                    modifier = contentModifier
                )
            } else {
                Row(modifier = contentModifier) {
                    ProductList(
                        products = products,
                        onProductSelected = onProductSelected,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                    ProductDetails(
                        product = selectedProduct,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }
            }
        } else {
            if (selectedProduct == null) {
                ProductList(
                    products = products,
                    onProductSelected = onProductSelected,
                    modifier = contentModifier
                )
            } else {
                ProductDetails(
                    product = selectedProduct,
                    modifier = contentModifier.verticalScroll(rememberScrollState()),
                    onBack = onBack
                )
            }
        }
    }
}

@Composable
fun ProductList(
    products: List<Product>,
    onProductSelected: (Product?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(products) { product ->
            ProductListItem(product = product, onProductSelected = onProductSelected)
        }
    }
}

@Composable
fun ProductGrid(
    products: List<Product>,
    onProductSelected: (Product?) -> Unit,
    modifier: Modifier = Modifier
) {
    val columns = when {
        LocalConfiguration.current.screenWidthDp < 600 -> 2
        else -> 3
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductGridItem(product = product, onProductSelected = onProductSelected)
        }
    }
}

@Composable
fun ProductListItem(
    product: Product,
    onProductSelected: (Product?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onProductSelected(product) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = product.imageResId),
            contentDescription = product.name,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = product.name,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun ProductGridItem(
    product: Product,
    onProductSelected: (Product?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onProductSelected(product) }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = product.imageResId),
            contentDescription = product.name,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = product.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1
        )
    }
}

@Composable
fun ProductDetails(
    product: Product?,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (onBack != null) {
            Button(onClick = onBack) {
                Text("Back")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (product != null) {
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.name,
                modifier = Modifier
                    .height(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Price: ${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            Text(
                text = "Select a product to view details.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
