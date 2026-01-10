package com.mamede.copa2022dadio

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.PausableComposition
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.mamede.copa2022dadio.ui.MainScreen
import com.mamede.copa2022dadio.ui.theme.Copa2022DaDIOTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Copa2022DaDIOTheme {
                //add lógica de permissão
                //context para VRF se já há permissão
                val context = LocalContext.current

                // criar lançador, aquela que abre a janela no android
                val permissionLaucher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        if (isGranted) {
                            //permissão concedida
                        } else {
                            //permissão negada
                        }
                    })

                // roda uma vez quando a tela abre
                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        Manifest.permission.POST_NOTIFICATIONS
                            .takeIf { permission ->
                                //usar o takeIF para retorna a permissão caso for TRUE
                                // VRF se não tem permissão
                                ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
                            }
                            ?.let { permission ->
                                //o let só vai funcionar se o takeIF não retorna null
                                permissionLaucher.launch((permission))
                            }
                    }
                }

//                LaunchedEffect(Unit) {
//                    Build.VERSION.SDK_INT.takeIf { it >= Build.VERSION_CODES.TIRAMISU }?.let {
//                        val permission = Manifest.permission.POST_NOTIFICATIONS
//                        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                            permissionLaucher.launch(permission)
//                        }
//                    }
//                }

//                // uma 3ª opção (estuda-lá)
//                // 1. Verifica versão
//                LaunchedEffect(Unit) {
//                    Build.VERSION.SDK_INT.takeIf { it >= Build.VERSION_CODES.TIRAMISU }
//                        // 2. Passa a string de permissão para o próximo passo
//                        ?.run { Manifest.permission.POST_NOTIFICATIONS }
//                        // 3. Filtra: Só continua se NÃO tiver permissão
//                        ?.takeIf { permission ->
//                            ContextCompat.checkSelfPermission(
//                                context,
//                                permission
//                            ) != PackageManager.PERMISSION_GRANTED
//                        }
//                        // 4. Executa o launcher
//                        ?.let { permission ->
//                            permissionLauncher.launch(permission)
//                        }
//                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                MainScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Copa2022DaDIOTheme {
        Greeting("Android")
    }
}