const { createServer } = require('http');
const { execFileSync } = require('child_process');

const PORT = 7777;

const HTML = `<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>DentApp Push</title>
<style>
*{box-sizing:border-box;margin:0;padding:0}
body{font-family:system-ui,sans-serif;background:#f0f4f8;min-height:100vh;display:flex;align-items:center;justify-content:center;padding:16px}
.card{background:#fff;border-radius:20px;padding:36px 32px;width:100%;max-width:460px;box-shadow:0 8px 32px rgba(0,0,0,.1)}
.logo{font-size:2.5rem;text-align:center;margin-bottom:8px}
h1{color:#1A6B8A;font-size:1.4rem;text-align:center;margin-bottom:6px}
p{color:#6b8a99;font-size:.9rem;text-align:center;margin-bottom:28px;line-height:1.5}
label{display:block;font-size:.8rem;font-weight:700;color:#1A6B8A;margin-bottom:6px;letter-spacing:.5px}
input{width:100%;border:2px solid #e3f4fa;border-radius:12px;padding:14px 16px;font-size:1rem;outline:none;transition:border .2s;background:#f8fcfe}
input:focus{border-color:#1A6B8A;background:#fff}
button{width:100%;margin-top:20px;background:#1A6B8A;color:#fff;border:none;border-radius:12px;padding:16px;font-size:1rem;font-weight:700;cursor:pointer;transition:all .2s}
button:hover:not(:disabled){background:#0d4f6b;transform:translateY(-1px)}
button:disabled{background:#aaa;cursor:not-allowed;transform:none}
#result{margin-top:20px;padding:16px;border-radius:12px;font-size:.85rem;line-height:1.6;display:none;word-break:break-word}
.ok{background:#e8f8f0;color:#1a7a4a;border:1px solid #a8e6c0}
.err{background:#fdf0ef;color:#c0392b;border:1px solid #f5b7b1}
.spin{display:inline-block;animation:spin 1s linear infinite}
@keyframes spin{to{transform:rotate(360deg)}}
</style>
</head>
<body>
<div class="card">
  <div class="logo">🦷</div>
  <h1>DentApp — GitHub Push</h1>
  <p>Pega tu token de GitHub.<br>Se usa una sola vez y no se guarda.</p>
  <label>TOKEN DE GITHUB</label>
  <input id="tok" type="password" placeholder="ghp_... o github_pat_..." autocomplete="off" autofocus>
  <button id="btn" onclick="push()">Subir a GitHub</button>
  <div id="result"></div>
</div>
<script>
async function push(){
  const tok = document.getElementById('tok').value.trim();
  if(!tok){alert('Pega el token primero');return}
  const btn=document.getElementById('btn');
  const res=document.getElementById('result');
  btn.disabled=true;
  btn.innerHTML='<span class="spin">⏳</span> Subiendo...';
  res.style.display='none';
  try{
    const r=await fetch('/push',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({t:tok})});
    const d=await r.json();
    res.style.display='block';
    if(d.ok){
      res.className='result ok';
      res.innerHTML='✅ <strong>Push exitoso</strong><br><br>'+d.out.replace(/\\n/g,'<br>');
      btn.textContent='✅ Listo — puedes cerrar esta página';
    }else{
      res.className='result err';
      res.innerHTML='❌ <strong>Error:</strong><br><br>'+d.err.replace(/\\n/g,'<br>');
      btn.disabled=false;
      btn.textContent='Reintentar';
    }
  }catch(e){
    res.style.display='block';res.className='result err';
    res.textContent='Error de red: '+e.message;
    btn.disabled=false;btn.textContent='Reintentar';
  }
}
document.getElementById('tok').addEventListener('keydown',e=>{if(e.key==='Enter')push()});
</script>
</body>
</html>`;

const server = createServer((req, res) => {
  if (req.method === 'GET') {
    res.writeHead(200, {'Content-Type':'text/html;charset=utf-8'});
    return res.end(HTML);
  }
  if (req.method === 'POST' && req.url === '/push') {
    let body = '';
    req.on('data', c => body += c);
    req.on('end', () => {
      let token;
      try { token = JSON.parse(body).t; } catch {
        res.writeHead(400,{'Content-Type':'application/json'});
        return res.end(JSON.stringify({ok:false,err:'JSON inválido'}));
      }
      if (!token || token.length < 20) {
        res.writeHead(400,{'Content-Type':'application/json'});
        return res.end(JSON.stringify({ok:false,err:'Token inválido'}));
      }
      const dir = '/root/dentapp-android';
      const remote = `https://x-access-token:${token}@github.com/andresgt1989/dentapp-android.git`;
      const clean  = 'https://github.com/andresgt1989/dentapp-android.git';
      try {
        execFileSync('/usr/bin/git',['-C',dir,'remote','set-url','origin',remote]);
        const out = execFileSync('/usr/bin/git',['-C',dir,'push','--set-upstream','origin','main'],{encoding:'utf8',stderr:'pipe'});
        execFileSync('/usr/bin/git',['-C',dir,'remote','set-url','origin',clean]);
        res.writeHead(200,{'Content-Type':'application/json'});
        res.end(JSON.stringify({ok:true,out:out||'Push completado.'}));
        console.log('Push exitoso. Cerrando en 5s...');
        setTimeout(()=>process.exit(0),5000);
      } catch(e) {
        try { execFileSync('/usr/bin/git',['-C',dir,'remote','set-url','origin',clean]); } catch{}
        const err = (e.stderr||'')+(e.stdout||'')+e.message;
        res.writeHead(200,{'Content-Type':'application/json'});
        res.end(JSON.stringify({ok:false,err}));
      }
    });
    return;
  }
  res.writeHead(404); res.end();
});

// Matar servidor viejo en puerto 7777 si existe
server.listen(PORT,'0.0.0.0',()=>{
  console.log(`\n🦷 Push helper → http://62.84.177.147:${PORT}\n`);
});
